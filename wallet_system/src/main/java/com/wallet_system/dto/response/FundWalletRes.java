package com.wallet_system.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundWalletRes {
    private String walletId;
    private BigDecimal fundedAmount;
}
