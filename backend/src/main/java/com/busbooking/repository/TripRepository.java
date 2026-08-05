package com.busbooking.repository;

import com.busbooking.model.BusTrip;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TripRepository {
    private final List<BusTrip> trips = new ArrayList<>();

    public List<BusTrip> findAll() {
        return new ArrayList<>(trips);
    }

    public Optional<BusTrip> findById(String tripId) {
        return trips.stream().filter(trip -> trip.getId().equals(tripId)).findFirst();
    }

    public List<BusTrip> search(String departure, String destination, LocalDate departureDate) {
        return trips.stream()
                .filter(trip -> matches(trip.getDeparture(), departure) && matches(trip.getDestination(), destination)
                        && (departureDate == null || departureDate.equals(trip.getDepartureDate())))
                .toList();
    }

    public BusTrip save(BusTrip trip) {
        trips.add(trip);
        return trip;
    }

    public BusTrip update(BusTrip trip) {
        delete(trip.getId());
        trips.add(trip);
        return trip;
    }

    public boolean delete(String tripId) {
        return trips.removeIf(trip -> trip.getId().equals(tripId));
    }

    public boolean exists(String tripId) {
        return findById(tripId).isPresent();
    }

    public long count() {
        return trips.size();
    }

    public void clear() {
        trips.clear();
    }

    private boolean matches(String value, String filter) {
        return filter == null || filter.isBlank()
                || value.toLowerCase(Locale.ROOT).contains(filter.trim().toLowerCase(Locale.ROOT));
    }
}
