package com.paypulse.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class FallbackService {

    public Map<String, Object> createFallbackResponse(String serviceName, String message) {
        return Map.of(
                "timestamp", java.time.LocalDateTime.now(),
                "status", 503,
                "error", "Service Unavailable",
                "message", message,
                "service", serviceName
        );
    }

    public ServiceInfo getServiceInfo(String service) {
        return switch (service) {
            case "auth" -> new ServiceInfo("auth-service", "Auth service temporarily unavailable (circuit breaker)");
            case "wallet" -> new ServiceInfo("wallet-service", "Wallet service temporarily unavailable (circuit breaker)");
            case "internal-wallet" -> new ServiceInfo("internal-wallet-service", "Internal wallet service temporarily unavailable (circuit breaker)");
            case "transaction" -> new ServiceInfo("transaction-service", "Transaction service temporarily unavailable (circuit breaker)");
            case "notification" -> new ServiceInfo("notification-service", "Notification service temporarily unavailable (circuit breaker)");
            case "analytics" -> new ServiceInfo("analytics-service", "Analytics service temporarily unavailable (circuit breaker)");
            default -> new ServiceInfo(service + "-service", "Service temporarily unavailable (circuit breaker)");
        };
    }

    public Mono<Map<String, Object>> processFallback(String service) {
        ServiceInfo info = getServiceInfo(service);
        log.warn("Fallback triggered for service: {}", info.name());
        return Mono.just(createFallbackResponse(info.name(), info.message()));
    }

    public record ServiceInfo(String name, String message) {
    }
}
