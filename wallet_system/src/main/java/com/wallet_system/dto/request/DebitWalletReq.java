package com.wallet_system.dto.request;

import java.math.BigDecimal;

import lombok.Data;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class DebitWalletReq {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

}
