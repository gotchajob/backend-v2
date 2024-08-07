package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCustomerFeedbackRepository extends JpaRepository<BookingCustomerFeedback, Long> {
    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.id = :id AND b.status != 0")
    BookingCustomerFeedback findById(long id);

    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.status != 0")
    List<BookingCustomerFeedback> findAll();
    @Query("SELECT b FROM BookingCustomerFeedback b WHERE b.bookingId =:bookingId AND b.status != 0")
    List<BookingCustomerFeedback> findByBookingId(long bookingId);


}
