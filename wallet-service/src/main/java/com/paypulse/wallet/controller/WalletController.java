package com.paypulse.wallet.controller;

import com.paypulse.common.BalanceResponse;
import com.paypulse.common.DepositRequest;
import com.paypulse.common.HistoryResponse;
import com.paypulse.common.WithdrawRequest;
import com.paypulse.wallet.service.OutboxProcessor;
import com.paypulse.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final OutboxProcessor outboxProcessor;

    @Operation(summary = "Получить баланс пользователя")
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceResponse getBalance() {
        return walletService.getBalance();
    }

    @Operation(summary = "Пополнить баланс")
    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void deposit(@Valid @RequestBody DepositRequest request) {
        walletService.deposit(request);
    }

    @Operation(summary = "Списать средства с кошелька")
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public boolean withdraw(@Valid @RequestBody WithdrawRequest request) {
        return walletService.withdraw(request);
    }

    @Operation(summary = "История операций пользователя")
    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public Page<HistoryResponse> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return walletService.getHistory(page, size, sortBy, sortDir);
    }

    @Operation(summary = "Вся история операций (ADMIN)")
    @GetMapping("/all-history")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<HistoryResponse> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return walletService.getAllHistory(page, size, sortBy, sortDir);
    }

    @Operation(summary = "Метрики outbox")
    @GetMapping("/outbox-metrics")
    @ResponseStatus(HttpStatus.OK)
    public OutboxProcessor.OutboxMetrics getOutboxMetrics() {
        return outboxProcessor.getMetrics();
    }

    @Operation(summary = "Админское пополнение баланса")
    @PostMapping("/admin/deposit/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void adminDeposit(@PathVariable UUID userId, @Valid @RequestBody DepositRequest request) {
        walletService.adminDeposit(userId.toString(), request);
    }

    @Operation(summary = "Внутреннее пополнение баланса")
    @PostMapping("/internal/deposit/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void internalDeposit(@PathVariable UUID userId, @Valid @RequestBody DepositRequest request) {
        walletService.adminDeposit(userId.toString(), request);
    }
}