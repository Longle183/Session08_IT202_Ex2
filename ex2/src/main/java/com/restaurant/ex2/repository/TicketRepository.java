package com.restaurant.ex2.repository;

import com.restaurant.ex2.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}