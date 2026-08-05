package com.busbooking.dto;

import com.busbooking.enums.UserRole;

public record AuthResponseDTO(String userId, String fullName, String phone, String email, UserRole role, String accessToken) {
}
