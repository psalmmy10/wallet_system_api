package com.wallet_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletRes {
    private String walletId;
    private BigDecimal balance;
}
