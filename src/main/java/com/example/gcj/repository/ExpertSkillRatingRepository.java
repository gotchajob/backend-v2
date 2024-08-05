package com.example.gcj.repository;

import com.example.gcj.model.ExpertSkillRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertSkillRatingRepository extends JpaRepository<ExpertSkillRating, Long> {
    ExpertSkillRating findById(long id);
    ExpertSkillRating findByBookingCustomerFeedbackIdAndExpertSkillOptionId(long feedbackId, long expertSkillOptionId);
}
