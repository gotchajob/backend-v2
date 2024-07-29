package com.example.gcj.dto.expert_question_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateExpertQuestionCategoryRequestDTO {
    private String category;
    private String description;
}
