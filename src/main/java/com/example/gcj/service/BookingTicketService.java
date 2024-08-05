package com.example.gcj.service;

import com.example.gcj.model.BookingTicket;

public interface BookingTicketService {
    BookingTicket create(long customerId);
    void useTicket(long customerId, long bookingId);

    boolean hadTicket(long customerId);
}
