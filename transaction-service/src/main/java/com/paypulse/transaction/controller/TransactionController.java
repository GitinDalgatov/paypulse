package com.paypulse.transaction.controller;

import com.paypulse.common.TransactionRequest;
import com.paypulse.common.TransactionResponse;
import com.paypulse.transaction.service.OutboxMetricsService;
import com.paypulse.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/transactions", "/transactions"})
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final OutboxMetricsService outboxMetricsService;

    @Operation(summary = "Создать транзакцию")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody TransactionRequest request) {
        return service.create(request);
    }

    @Operation(summary = "История транзакций пользователя")
    @GetMapping({"/", "/history", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getHistory() {
        return service.getHistory();
    }

    @Operation(summary = "Метрики outbox")
    @GetMapping("/outbox-metrics")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getOutboxMetrics() {
        return outboxMetricsService.getDefaultMetrics();
    }
}