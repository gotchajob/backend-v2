package com.example.gcj.Service_Layer.service;

import com.example.gcj.Repository_Layer.model.BookingTicket;

public interface BookingTicketService {
    BookingTicket create(long customerId);
    void useTicket(long customerId, long bookingId);

    boolean hadTicket(long customerId);

    boolean validTicketWhenCancelBooking(long bookingId);
}
