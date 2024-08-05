package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackQuestionRepository extends JpaRepository<BookingExpertFeedbackQuestion, Long> {
    BookingExpertFeedbackQuestion findById(long id);

    List<BookingExpertFeedbackQuestion> findByCreatedBy(long expertId);
}
