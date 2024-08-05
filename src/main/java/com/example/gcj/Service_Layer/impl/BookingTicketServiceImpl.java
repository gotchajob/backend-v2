package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingTicket;
import com.example.gcj.Repository_Layer.repository.BookingTicketRepository;
import com.example.gcj.Service_Layer.service.BookingTicketService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingTicketServiceImpl implements BookingTicketService {
    private final BookingTicketRepository bookingTicketRepository;

    @Override
    public BookingTicket create(long customerId) {
        BookingTicket bookingTicket = BookingTicket
                .builder()
                .customerId(customerId)
                .expireDate(LocalDateTime.now().plusDays(30))
                .status(1)
                .build();
        return bookingTicketRepository.save(bookingTicket);
    }

    @Override
    public void useTicket(long customerId, long bookingId) {
        BookingTicket bookingTicket = bookingTicketRepository.findFirstByExpireDateAfterNow(LocalDateTime.now(), customerId);
        if (bookingTicket == null) {
            throw new CustomException("dont had any ticket to use");
        }

        bookingTicket.setStatus(3);
        bookingTicket.setBookingId(bookingId);
        bookingTicket.setDateUsed(LocalDateTime.now());
        bookingTicketRepository.save(bookingTicket);
    }

    @Override
    public boolean hadTicket(long customerId) {
        return bookingTicketRepository.hadTicket(LocalDateTime.now(), customerId);
    }

    @Override
    public boolean validTicketWhenCancelBooking(long bookingId) {
        BookingTicket bookingTicket = bookingTicketRepository.findByBookingIdAndStatus(bookingId, 3);
        if (bookingTicket == null) {
            return false;
        }

        bookingTicket.setStatus(1);
        bookingTicketRepository.save(bookingTicket);
        return true;
    }
}
