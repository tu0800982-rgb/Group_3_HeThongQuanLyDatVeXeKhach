package com.busbooking.controller;

import com.busbooking.dto.ApiResponse;
import com.busbooking.dto.SeatResponseDTO;
import com.busbooking.dto.TripResponseDTO;
import com.busbooking.enums.SeatType;
import com.busbooking.service.TripService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponseDTO>>> getAllTrips() {
        return ResponseEntity.ok(ApiResponse.success("Trips retrieved successfully", tripService.getAllTrips()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TripResponseDTO>>> searchTrips(
            @RequestParam(required = false) String departure, @RequestParam(required = false) String destination,
            @RequestParam(required = false) LocalDate departureDate, @RequestParam(required = false) SeatType seatType,
            @RequestParam(required = false) BigDecimal minimumPrice,
            @RequestParam(required = false) BigDecimal maximumPrice, @RequestParam(required = false) String busType) {
        return ResponseEntity.ok(ApiResponse.success("Matching trips retrieved successfully", tripService
                .searchTrips(departure, destination, departureDate, seatType, minimumPrice, maximumPrice, busType)));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripResponseDTO>> getTripById(@PathVariable String tripId) {
        return ResponseEntity.ok(ApiResponse.success("Trip retrieved successfully", tripService.getTripById(tripId)));
    }

    @GetMapping("/{tripId}/seats")
    public ResponseEntity<ApiResponse<List<SeatResponseDTO>>> getSeatMap(@PathVariable String tripId) {
        return ResponseEntity
                .ok(ApiResponse.success("Seat map retrieved successfully", tripService.getSeatMap(tripId)));
    }
}
