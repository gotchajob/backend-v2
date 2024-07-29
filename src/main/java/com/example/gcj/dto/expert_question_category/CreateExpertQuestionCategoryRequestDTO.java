package com.example.gcj.dto.expert_question_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateExpertQuestionCategoryRequestDTO {
    private String category;
    private String description;
}
