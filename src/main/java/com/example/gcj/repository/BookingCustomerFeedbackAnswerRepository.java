package com.example.gcj.repository;

import com.example.gcj.model.BookingCustomerFeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCustomerFeedbackAnswerRepository extends JpaRepository<BookingCustomerFeedbackAnswer, Long> {
    List<BookingCustomerFeedbackAnswer> findByBookingCustomerFeedbackId(long bookingCustomerFeedbackId);
}
