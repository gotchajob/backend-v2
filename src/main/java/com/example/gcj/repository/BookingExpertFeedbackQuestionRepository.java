package com.example.gcj.repository;

import com.example.gcj.model.BookingExpertFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackQuestionRepository extends JpaRepository<BookingExpertFeedbackQuestion, Long> {
    BookingExpertFeedbackQuestion findById(long id);

    List<BookingExpertFeedbackQuestion> findByCreatedBy(long expertId);
}
