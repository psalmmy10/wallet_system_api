package com.wallet_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import com.wallet_system.constant.TransactioType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private TransactioType transactionType;
    private BigDecimal amount;
    private Instant createdOn;
}
