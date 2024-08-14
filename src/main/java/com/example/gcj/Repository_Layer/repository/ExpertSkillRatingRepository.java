package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertSkillRating;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingTotalRatingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertSkillRatingRepository extends JpaRepository<ExpertSkillRating, Long> {
    ExpertSkillRating findById(long id);
    ExpertSkillRating findByBookingCustomerFeedbackIdAndExpertSkillOptionId(long feedbackId, long expertSkillOptionId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingTotalRatingResponseDTO(esr.rating, count(*)) " +
            "FROM ExpertSkillRating esr " +
            "WHERE esr.expertSkillOption.id =:expertSkillOptionId " +
            "group by esr.rating " +
            "order by esr.rating ")
    List<ExpertSkillRatingTotalRatingResponseDTO> getTotalRating(long expertSkillOptionId);

    Page<ExpertSkillRating> findByExpertSkillOptionId(long expertSkillOptionId, Pageable pageable);
}
