package com.paypulse.notification.controller;

import com.paypulse.common.NotificationResponse;
import com.paypulse.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @Operation(summary = "Получить уведомления пользователя")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationResponse> getNotifications() {
        return service.getNotifications();
    }
}