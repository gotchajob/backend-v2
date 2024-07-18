package com.example.gcj.repository;

import com.example.gcj.model.BookingCustomerFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCustomerFeedbackQuestionRepository extends JpaRepository<BookingCustomerFeedbackQuestion, Long> {
    BookingCustomerFeedbackQuestion findById(long id);
}
