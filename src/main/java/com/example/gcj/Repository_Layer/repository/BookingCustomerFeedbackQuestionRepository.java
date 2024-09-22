package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCustomerFeedbackQuestionRepository extends JpaRepository<BookingCustomerFeedbackQuestion, Long> {
    @Query("SELECT bcfq FROM BookingCustomerFeedbackQuestion bcfq WHERE bcfq.id =:id AND bcfq.status != 0")
    BookingCustomerFeedbackQuestion findById(long id);
    @Query("SELECT bcfq FROM BookingCustomerFeedbackQuestion bcfq WHERE bcfq.status != 0")
    List<BookingCustomerFeedbackQuestion> findAll();
}
