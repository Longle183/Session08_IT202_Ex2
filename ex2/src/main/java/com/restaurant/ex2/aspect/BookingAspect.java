package com.restaurant.ex2.aspect;

import com.restaurant.ex2.dto.BookTicketRequest;
import com.restaurant.ex2.entity.Flight;
import com.restaurant.ex2.entity.Ticket;
import com.restaurant.ex2.repository.FlightRepository;
import com.restaurant.ex2.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class BookingAspect {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Around("execution(* com.restaurant.ex2.service.TicketService.bookTicket(..))")
    public Object sanitizeData(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        BookTicketRequest request = (BookTicketRequest) args[0];

        String cleanName = request.getPassengerName()
                .trim()
                .toUpperCase();

        request.setPassengerName(cleanName);

        args[0] = request;

        return joinPoint.proceed(args);
    }

    @Before("execution(* com.restaurant.ex2.service.TicketService.cancelTicket(..)) && args(ticketId)")
    public void checkCancelRule(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Flight flight = flightRepository.findById(ticket.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        long hours = Duration.between(
                LocalDateTime.now(),
                flight.getDepartureTime()
        ).toHours();

        if (hours < 24) {
            throw new RuntimeException("Không thể hủy vé trước giờ bay dưới 24h");
        }
    }
}