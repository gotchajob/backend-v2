package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCustomerFeedbackAnswerRepository extends JpaRepository<BookingCustomerFeedbackAnswer, Long> {
    List<BookingCustomerFeedbackAnswer> findByBookingCustomerFeedbackId(long bookingCustomerFeedbackId);
}
