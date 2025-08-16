package com.paypulse.wallet.controller;

import com.paypulse.wallet.service.OutboxProcessor;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/outbox")
@RequiredArgsConstructor
public class OutboxController {

    private final OutboxProcessor outboxProcessor;

    @GetMapping("/metrics")
    @Operation(summary = "Get outbox metrics")
    @ResponseStatus(HttpStatus.OK)
    public OutboxProcessor.OutboxMetrics getMetrics() {
        return outboxProcessor.getMetrics();
    }
}