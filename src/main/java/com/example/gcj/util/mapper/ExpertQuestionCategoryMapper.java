package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.model.ExpertQuestionCategory;

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
