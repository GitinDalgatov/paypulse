package com.paypulse.auth.controller;

import com.paypulse.auth.dto.AuthResponse;
import com.paypulse.auth.dto.LoginRequest;
import com.paypulse.auth.dto.RefreshRequest;
import com.paypulse.auth.dto.RegisterRequest;
import com.paypulse.auth.config.JwtService;
import com.paypulse.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Auth API", description = "Аутентификация и управление пользователями")
@RestController
@RequestMapping({"/api/auth", "/auth"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @Operation(summary = "Логин пользователя")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @Operation(summary = "Обновление токена")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest req) {
        return authService.refresh(req);
    }

    @Operation(summary = "Назначение роли пользователю")
    @PostMapping("/assign-role")
    @ResponseStatus(HttpStatus.OK)
    public String assignRole(@RequestParam String username, @RequestParam String role) {
        authService.assignRole(username, role);
        return "Role assigned successfully";
    }

    @Operation(summary = "Выход из системы")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout(@RequestHeader("Authorization") String authHeader) {
        return authService.logout(authHeader);
    }

    @Operation(summary = "Валидация токена")
    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public Boolean validateToken(@RequestParam String token) {
        return jwtService.validateToken(token);
    }

    @Operation(summary = "Health check")
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> health() {
        return authService.getHealthStatus();
    }
}