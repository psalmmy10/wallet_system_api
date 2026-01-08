package com.wallet_system.repository;

import com.wallet_system.constant.TransactioType;
import com.wallet_system.dao.Transaction;
import com.wallet_system.dao.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletAndTransactionTypeOrderByCreatedOnDesc(Wallet wallet, TransactioType type);
}
