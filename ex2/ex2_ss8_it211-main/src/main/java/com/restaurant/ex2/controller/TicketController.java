package com.restaurant.ex2.controller;

import com.restaurant.ex2.dto.BookTicketRequest;
import com.restaurant.ex2.entity.Ticket;
import com.restaurant.ex2.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(
            @Valid @RequestBody BookTicketRequest request
    ) {

        Ticket ticket = ticketService.bookTicket(request);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @PostMapping("/cancel/{ticketId}")
    public ResponseEntity<?> cancelTicket(
            @PathVariable Long ticketId
    ) {

        return ResponseEntity.ok(
                ticketService.cancelTicket(ticketId)
        );
    }
}