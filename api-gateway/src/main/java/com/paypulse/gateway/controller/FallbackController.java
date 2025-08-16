package com.paypulse.gateway.controller;

import com.paypulse.gateway.service.FallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@Slf4j
public class FallbackController {

    private final FallbackService fallbackService;

    public FallbackController(FallbackService fallbackService) {
        this.fallbackService = fallbackService;
    }

    @GetMapping("/fallback/{service}")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Map<String, Object>> fallback(@PathVariable String service) {
        return fallbackService.processFallback(service);
    }
}