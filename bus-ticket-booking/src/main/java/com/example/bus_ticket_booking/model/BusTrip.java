package com.example.bus_ticket_booking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusTrip {
    private String tripId;
    private String diemDi;
    private String diemDen;
    private LocalDateTime thoiGianKhoiHanh;
    private String bienSoXe;
    private int tongSoGhe;
    //thieu danh sach ghe
    List<BusTrip> dsGhe = new ArrayList<>();
}
