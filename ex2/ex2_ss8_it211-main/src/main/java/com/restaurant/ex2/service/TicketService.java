package com.restaurant.ex2.service;

import com.restaurant.ex2.dto.BookTicketRequest;
import com.restaurant.ex2.entity.Flight;
import com.restaurant.ex2.entity.Ticket;
import com.restaurant.ex2.repository.FlightRepository;
import com.restaurant.ex2.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    public Ticket bookTicket(BookTicketRequest request) {

        Flight flight = flightRepository
                .findByFlightNumber(request.getFlightNumber())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("Hết vé");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        flightRepository.save(flight);

        Ticket ticket = new Ticket();

        ticket.setPassengerName(request.getPassengerName());
        ticket.setFlightId(flight.getId());
        ticket.setStatus("BOOKED");

        return ticketRepository.save(ticket);
    }

    public String cancelTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus("CANCELED");

        ticketRepository.save(ticket);

        return "Hủy vé thành công";
    }
}