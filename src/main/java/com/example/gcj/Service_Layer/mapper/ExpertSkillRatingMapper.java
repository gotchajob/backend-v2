package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.ExpertSkillRating;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingResponseDTO;

public class ExpertSkillRatingMapper {
    public static ExpertSkillRatingResponseDTO toDto(ExpertSkillRating expertSkillRating) {
        if (expertSkillRating == null) {
            return null;
        }

        return ExpertSkillRatingResponseDTO
                .builder()
                .id(expertSkillRating.getId())
                .expertSkillOptionId(expertSkillRating.getExpertSkillOption().getId())
                .rating(expertSkillRating.getRating())
                .comment(expertSkillRating.getComment())
                .createdAt(expertSkillRating.getCreatedAt())
                .build();
    }
}
