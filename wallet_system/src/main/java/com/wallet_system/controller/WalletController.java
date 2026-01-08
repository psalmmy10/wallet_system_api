package com.wallet_system.controller;

import com.wallet_system.constant.TransactioType;
import com.wallet_system.dao.Transaction;
import com.wallet_system.dto.request.DebitWalletReq;
import com.wallet_system.dto.request.FundWalletReq;
import com.wallet_system.dto.response.FundWalletRes;
import com.wallet_system.dto.response.TransactionDto;
import com.wallet_system.dto.response.WalletRes;
import com.wallet_system.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Slf4j
@Validated
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "Create a new wallet for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet created successfully",
                    content = @Content(schema = @Schema(implementation = WalletRes.class))),
            @ApiResponse(responseCode = "400", description = "Wallet already exists for user",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/create-wallet")
    public ResponseEntity<WalletRes> createWallet(@RequestParam String userId) {
        log.info("Creating wallet for userId: {}", userId);
        WalletRes walletRes = walletService.createWallet(userId);
        return ResponseEntity.ok(walletRes);
    }

    @Operation(summary = "Get transaction history by type (DEBIT or CREDIT)")
    @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully",
                        content = @Content(schema = @Schema(implementation = TransactionDto.class))),
                @ApiResponse(responseCode = "404", description = "Wallet not found",
                        content = @Content(schema = @Schema(implementation = String.class)))
     })
        @GetMapping("/{walletId}/transactions")
        public ResponseEntity<List<TransactionDto>> getTransactionHistory(
                @PathVariable Long walletId,
                @RequestParam TransactioType type) {
        log.info("Fetching {} transactions for walletId {}", type, walletId);
        List<TransactionDto> transactions = walletService.getTransactionHistory(walletId, type);
        return ResponseEntity.ok(transactions);
    }


    @Operation(summary = "Fund a wallet")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet funded successfully",
                    content = @Content(schema = @Schema(implementation = FundWalletRes.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid amount",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/{walletId}/fund")
    public ResponseEntity<FundWalletRes> fundWallet(
            @PathVariable Long walletId,
            @Valid @RequestBody FundWalletReq request
    ) {
        log.info("Funding wallet {} with amount {}", walletId, request.getAmount());
        FundWalletRes res = walletService.fundWallet(walletId, request);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Debit a wallet")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet debited successfully",
                    content = @Content(schema = @Schema(implementation = WalletRes.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Insufficient balance or invalid amount",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/{walletId}/debit")
    public ResponseEntity<WalletRes> debitWallet(
            @PathVariable Long walletId,
            @Valid @RequestBody DebitWalletReq request
    ) {
        log.info("Debiting wallet {} with amount {}", walletId, request.getAmount());
        WalletRes res = walletService.debitWallet(walletId, request);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Get wallet details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet retrieved successfully",
                    content = @Content(schema = @Schema(implementation = WalletRes.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletRes> getWalletDetails(@PathVariable Long walletId) {
        log.info("Fetching wallet details for walletId {}", walletId);
        WalletRes walletRes = walletService.getWalletDetails(walletId);
        return ResponseEntity.ok(walletRes);
    }

}