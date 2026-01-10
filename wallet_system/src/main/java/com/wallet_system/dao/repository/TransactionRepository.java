package com.wallet_system.dao.repository;

import com.wallet_system.constant.TransactioType;
import com.wallet_system.dao.entity.Transaction;
import com.wallet_system.dao.entity.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletAndTransactionTypeOrderByCreatedOnDesc(Wallet wallet, TransactioType type);
}
