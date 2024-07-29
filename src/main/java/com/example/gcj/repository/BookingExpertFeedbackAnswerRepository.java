package com.example.gcj.repository;

import com.example.gcj.model.BookingExpertFeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackAnswerRepository extends JpaRepository<BookingExpertFeedbackAnswer, Long> {
    BookingExpertFeedbackAnswer findById(long id);
    List<BookingExpertFeedbackAnswer> findByFeedbackId(long feedbackId);
}
