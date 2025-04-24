package ru.spbpu.collections.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.collections.exceptions.TokenRefreshException;
import ru.spbpu.collections.model.request.LoginRequest;
import ru.spbpu.collections.model.request.TokenRefreshRequest;
import ru.spbpu.collections.model.response.JwtResponse;
import ru.spbpu.collections.model.response.TokenRefreshResponse;
import ru.spbpu.collections.service.AuthService;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.base-prefix}")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody final TokenRefreshRequest request) {

        if (isBlank(request.getRefreshToken())) {
            throw new TokenRefreshException("Refresh token is empty");
        }

        return ok(authService.refreshToken(request));
    }
}
