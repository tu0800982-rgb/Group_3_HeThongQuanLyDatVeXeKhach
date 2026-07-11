package com.example.bus_ticket_booking.model;

public class Staff extends User{
    private String chucVu;

    public Staff(String userId, String fullName, String phoneNumber, String email, String chucVu) {
        super(userId, fullName, phoneNumber, email);
        this.chucVu = chucVu;
    }
}
