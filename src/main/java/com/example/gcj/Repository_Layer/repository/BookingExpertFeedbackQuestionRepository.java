package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingExpertFeedbackQuestionRepository extends JpaRepository<BookingExpertFeedbackQuestion, Long> {
    @Query("SELECT befq FROM BookingExpertFeedbackQuestion befq WHERE befq.status != 0 AND befq.id =:id ")
    BookingExpertFeedbackQuestion findById(long id);
    @Query("SELECT befq FROM BookingExpertFeedbackQuestion befq WHERE befq.status != 0 AND befq.createdBy =:expertId ")
    List<BookingExpertFeedbackQuestion> findByCreatedBy(long expertId);
    @Query("SELECT befq FROM BookingExpertFeedbackQuestion befq WHERE befq.status != 0")
    List<BookingExpertFeedbackQuestion> findAll();


}
