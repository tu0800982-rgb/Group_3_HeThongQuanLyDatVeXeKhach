package com.busbooking.dto;

import com.busbooking.enums.SeatStatus;
import com.busbooking.enums.SeatType;

public class SeatResponseDTO {
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus status;

    public SeatResponseDTO() {
    }

    public SeatResponseDTO(String seatNumber, SeatType seatType, SeatStatus status) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.status = status;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
