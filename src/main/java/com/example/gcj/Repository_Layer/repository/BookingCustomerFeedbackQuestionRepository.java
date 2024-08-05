package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCustomerFeedbackQuestionRepository extends JpaRepository<BookingCustomerFeedbackQuestion, Long> {
    BookingCustomerFeedbackQuestion findById(long id);
}
