package com.example.gcj.Service_Layer.dto.expert_question_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateExpertQuestionCategoryRequestDTO {
    private String category;
    private String description;
}
