package com.wallet_system.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.wallet_system.constant.TransactioType;
import com.wallet_system.dao.entity.Transaction;
import com.wallet_system.dao.entity.User;
import com.wallet_system.dao.entity.Wallet;
import com.wallet_system.dao.repository.TransactionRepository;
import com.wallet_system.dao.repository.UserRepository;
import com.wallet_system.dao.repository.WalletRepository;
import com.wallet_system.dto.request.DebitWalletReq;
import com.wallet_system.dto.request.FundWalletReq;
import com.wallet_system.dto.response.FundWalletRes;
import com.wallet_system.dto.response.TransactionDto;
import com.wallet_system.dto.response.WalletRes;
import com.wallet_system.exception.BusinessRuleException;
import com.wallet_system.exception.WalletNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public WalletRes createWallet(String userId) {
        
        User user = userRepository.findByUserId(userId)
                .orElseGet(() -> {
                    return userRepository.save(User.builder()
                            .userId(userId)
                            .build());
                });

        walletRepository.findByUserId(user.getId())
                .ifPresent(w -> {
                    throw new BusinessRuleException("Wallet already exists for this user");
                });

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        Wallet savedWallet = walletRepository.save(wallet);

        return mapToWalletRes(savedWallet);
    }


    @Transactional
    public FundWalletRes fundWallet(Long walletId, FundWalletReq req) {
        validateAmount(req.getAmount());

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(req.getAmount()));
        walletRepository.save(wallet);

        transactionRepository.save(Transaction.builder()
                .wallet(wallet)
                .transactionType(TransactioType.CREDIT)
                .amount(req.getAmount())
                .createdOn(Instant.now())
                .build());

        return mapToFundWalletRes(wallet, req.getAmount());
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionHistory(Long walletId, TransactioType type) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        List<Transaction> transactions = transactionRepository.findByWalletAndTransactionTypeOrderByCreatedOnDesc(wallet, type);

        return transactions.stream()
                .map(tx -> TransactionDto.builder()
                        .transactionType(tx.getTransactionType())
                        .amount(tx.getAmount())
                        .createdOn(tx.getCreatedOn())
                        .build())
                .toList();
    }


    @Transactional
    public WalletRes debitWallet(Long walletId, DebitWalletReq req) {
        validateAmount(req.getAmount());

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (wallet.getBalance().compareTo(req.getAmount()) < 0) {
            throw new BusinessRuleException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(req.getAmount()));
        walletRepository.save(wallet);

        transactionRepository.save(Transaction.builder()
                .wallet(wallet)
                .transactionType(TransactioType.DEBIT)
                .amount(req.getAmount())
                .createdOn(Instant.now())
                .build());

        return mapToWalletRes(wallet);
    }


    @Transactional(readOnly = true)
    public WalletRes getWalletDetails(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return mapToWalletRes(wallet);
    }

    private WalletRes mapToWalletRes(Wallet wallet) {
        return new WalletRes(
                wallet.getId().toString(),
                wallet.getBalance()
        );
    }

    private FundWalletRes mapToFundWalletRes(Wallet wallet, BigDecimal fundedAmount) {
        return new FundWalletRes(
                wallet.getId().toString(),
                fundedAmount
        );
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Amount must be greater than zero");
        }
    }
}