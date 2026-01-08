package com.wallet_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wallet_system.dao.Wallet;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);

    Optional<Wallet> findByIdAndUserId(int walletId, Long userId);

    Optional<Wallet> findByIdAndUserId(Long walletId, Long userId);
}