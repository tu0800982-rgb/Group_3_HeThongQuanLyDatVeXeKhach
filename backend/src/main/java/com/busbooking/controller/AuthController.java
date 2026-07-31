package com.busbooking.controller;

import com.busbooking.dto.ApiResponse;
import com.busbooking.dto.AuthResponseDTO;
import com.busbooking.dto.LoginRequestDTO;
import com.busbooking.dto.RegisterRequestDTO;
import com.busbooking.dto.ProfileUpdateRequestDTO;
import com.busbooking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đăng ký thành công", authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", authService.login(request)));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> updateProfile(
            @RequestHeader(value = "X-Session-Token", required = false) String token,
            @Valid @RequestBody ProfileUpdateRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật hồ sơ thành công", authService.updateProfile(token, request)));
    }
}
