package com.busbooking.dto;

import com.busbooking.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookingRequestDTO {
    @NotBlank
    @Size(min = 2, max = 100)
    private String customerName;
    @NotBlank
    @Pattern(regexp = "\\d{10}")
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private CustomerType customerType;
    @NotBlank
    private String tripId;
    @NotBlank
    private String seatNumber;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(String customerName, String phone, String email, CustomerType customerType, String tripId,
            String seatNumber) {
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.customerType = customerType;
        this.tripId = tripId;
        this.seatNumber = seatNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
