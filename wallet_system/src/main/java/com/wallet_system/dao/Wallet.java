package com.wallet_system.dao;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.wallet_system.constant.TransactioType;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    private TransactioType transactionType;;
}