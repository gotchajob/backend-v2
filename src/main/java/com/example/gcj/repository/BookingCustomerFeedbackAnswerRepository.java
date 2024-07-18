package com.example.gcj.repository;

import com.example.gcj.model.BookingCustomerFeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCustomerFeedbackAnswerRepository extends JpaRepository<BookingCustomerFeedbackAnswer, Long> {

}
