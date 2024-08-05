package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.Repository_Layer.model.ExpertQuestionCategory;

public class ExpertQuestionCategoryMapper {
    public static ExpertQuestionCategoryListResponseDTO toDto(ExpertQuestionCategory expertQuestionCategory) {
        if (expertQuestionCategory == null) {
            return null;
        }

        return ExpertQuestionCategoryListResponseDTO
                .builder()
                .id(expertQuestionCategory.getId())
                .category(expertQuestionCategory.getCategory())
                .description(expertQuestionCategory.getDescription())
                .build();
    }
}
