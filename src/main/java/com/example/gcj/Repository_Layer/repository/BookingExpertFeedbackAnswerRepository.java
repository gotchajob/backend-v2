package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackAnswerRepository extends JpaRepository<BookingExpertFeedbackAnswer, Long> {
    BookingExpertFeedbackAnswer findById(long id);
    List<BookingExpertFeedbackAnswer> findByFeedbackId(long feedbackId);
}
