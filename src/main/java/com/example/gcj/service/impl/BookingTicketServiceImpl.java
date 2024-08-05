package com.example.gcj.service.impl;

import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BookingTicket;
import com.example.gcj.repository.BookingTicketRepository;
import com.example.gcj.service.BookingTicketService;
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
}
