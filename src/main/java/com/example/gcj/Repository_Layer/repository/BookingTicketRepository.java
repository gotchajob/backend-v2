package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookingTicketRepository extends JpaRepository<BookingTicket, Long> {
    BookingTicket findById(long id);

    @Query("SELECT b FROM BookingTicket b WHERE b.expireDate > :now AND b.customerId =:customerId AND b.status = 1 ORDER BY b.expireDate ASC")
    BookingTicket findFirstByExpireDateAfterNow(@Param("now") LocalDateTime now, long customerId);

    @Query("SELECT count(b) > 0 FROM BookingTicket b WHERE b.expireDate > :now AND b.customerId =:customerId AND b.status = 1")
    boolean hadTicket(@Param("now") LocalDateTime now, long customerId);

    BookingTicket findByBookingIdAndStatus(long bookingId, int status);
}
