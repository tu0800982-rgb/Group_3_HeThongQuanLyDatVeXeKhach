package com.busbooking.dto;

import jakarta.validation.constraints.Pattern;

public class LoginRequestDTO {
    @Pattern(regexp = "\\d{10}")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
