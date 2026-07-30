package com.busbooking.repository;

import com.busbooking.model.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository {
    private final List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets);
    }

    public Optional<Ticket> findById(String ticketId) {
        return tickets.stream().filter(ticket -> ticket.getId().equals(ticketId)).findFirst();
    }

    public Ticket save(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

    public Ticket update(Ticket ticket) {
        delete(ticket.getId());
        tickets.add(ticket);
        return ticket;
    }

    public boolean delete(String ticketId) {
        return tickets.removeIf(ticket -> ticket.getId().equals(ticketId));
    }

    public boolean exists(String ticketId) {
        return findById(ticketId).isPresent();
    }

    public long count() {
        return tickets.size();
    }

    public void clear() {
        tickets.clear();
    }
}
