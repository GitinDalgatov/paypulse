package com.paypulse.transaction.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OutboxMetricsService {

    public Map<String, Object> getDefaultMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("pendingMessages", 0);
        metrics.put("processedMessages", 0);
        metrics.put("failedMessages", 0);
        metrics.put("lastProcessedAt", null);
        return metrics;
    }
}
