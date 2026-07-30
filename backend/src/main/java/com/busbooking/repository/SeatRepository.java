package com.busbooking.repository;

import com.busbooking.enums.SeatStatus;
import com.busbooking.model.Seat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SeatRepository {
    private final List<Seat> seats = new ArrayList<>();

    public List<Seat> findAll() {
        return new ArrayList<>(seats);
    }

    public List<Seat> findByTripId(String tripId) {
        return seats.stream().filter(seat -> seat.getTripId().equals(tripId)).toList();
    }

    public Optional<Seat> findSeat(String tripId, String seatNumber) {
        return seats.stream()
                .filter(seat -> seat.getTripId().equals(tripId) && seat.getSeatNumber().equalsIgnoreCase(seatNumber))
                .findFirst();
    }

    public Seat save(Seat seat) {
        seats.add(seat);
        return seat;
    }

    public Seat updateSeat(Seat seat) {
        delete(seat.getTripId(), seat.getSeatNumber());
        seats.add(seat);
        return seat;
    }

    public boolean delete(String tripId, String seatNumber) {
        return seats
                .removeIf(seat -> seat.getTripId().equals(tripId) && seat.getSeatNumber().equalsIgnoreCase(seatNumber));
    }

    public boolean exists(String tripId, String seatNumber) {
        return findSeat(tripId, seatNumber).isPresent();
    }

    public long countAvailableSeats(String tripId) {
        return seats.stream()
                .filter(seat -> seat.getTripId().equals(tripId) && seat.getStatus() == SeatStatus.AVAILABLE).count();
    }

    public long countSeats(String tripId) {
        return seats.stream().filter(seat -> seat.getTripId().equals(tripId)).count();
    }

    public long countBookedSeats(String tripId) {
        return seats.stream().filter(seat -> seat.getTripId().equals(tripId) && seat.getStatus() == SeatStatus.BOOKED)
                .count();
    }

    public void clear() {
        seats.clear();
    }
}
