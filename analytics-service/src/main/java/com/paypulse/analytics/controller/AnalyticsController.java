package com.paypulse.analytics.controller;

import com.paypulse.analytics.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Operation(summary = "Получить аналитику пользователя")
    @GetMapping("/summary")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getSummary() {
        return analyticsService.getUserAnalytics();
    }

    @Operation(summary = "Получить события баланса")
    @GetMapping("/balance-events")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getBalanceEvents() {
        return analyticsService.getBalanceEvents();
    }

    @Operation(summary = "Получить события транзакций")
    @GetMapping("/transaction-events")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTransactionEvents() {
        return analyticsService.getTransactionEvents();
    }
}