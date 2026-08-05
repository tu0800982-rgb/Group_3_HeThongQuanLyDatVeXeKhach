package com.busbooking.service;

import com.busbooking.dto.SeatResponseDTO;
import com.busbooking.dto.TripResponseDTO;
import com.busbooking.enums.SeatType;
import com.busbooking.exception.TripNotFoundException;
import com.busbooking.model.BusTrip;
import com.busbooking.repository.SeatRepository;
import com.busbooking.repository.TripRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;

    public TripService(TripRepository tripRepository, SeatRepository seatRepository) {
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
    }

    public List<TripResponseDTO> getAllTrips() {
        return tripRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TripResponseDTO getTripById(String tripId) {
        return toResponse(requireTrip(tripId));
    }

    public List<TripResponseDTO> searchTrips(String departure, String destination, LocalDate departureDate,
            SeatType seatType) {
        return tripRepository.search(departure, destination, departureDate).stream().filter(trip -> seatType == null
                || seatRepository.findByTripId(trip.getId()).stream().anyMatch(seat -> seat.getSeatType() == seatType))
                .map(this::toResponse).toList();
    }

    public List<TripResponseDTO> searchTrips(String departure, String destination, LocalDate departureDate,
            SeatType seatType, BigDecimal minimumPrice, BigDecimal maximumPrice, String busType) {
        return searchTrips(departure, destination, departureDate, seatType).stream()
                .filter(trip -> minimumPrice == null || trip.getBasePrice().compareTo(minimumPrice) >= 0)
                .filter(trip -> maximumPrice == null || trip.getBasePrice().compareTo(maximumPrice) <= 0)
                .filter(trip -> busType == null || busType.isBlank()
                        || trip.getBusType().equalsIgnoreCase(busType.trim()))
                .toList();
    }

    public List<SeatResponseDTO> getSeatMap(String tripId) {
        requireTrip(tripId);
        return seatRepository.findByTripId(tripId).stream()
                .map(seat -> new SeatResponseDTO(seat.getSeatNumber(), seat.getSeatType(), seat.getStatus())).toList();
    }

    public long countAvailableSeats(String tripId) {
        requireTrip(tripId);
        return seatRepository.countAvailableSeats(tripId);
    }

    public long countBookedSeats(String tripId) {
        requireTrip(tripId);
        return seatRepository.countBookedSeats(tripId);
    }

    private BusTrip requireTrip(String tripId) {
        return tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
    }

    private TripResponseDTO toResponse(BusTrip trip) {
        return new TripResponseDTO(trip.getId(), trip.getBusCompany(), trip.getBusType(), trip.getDeparture(),
                trip.getDestination(), trip.getDepartureDate(), trip.getDepartureTime(), trip.getArrivalTime(),
                trip.getBasePrice(), (int) seatRepository.countAvailableSeats(trip.getId()),
                (int) seatRepository.countSeats(trip.getId()));
    }
}
