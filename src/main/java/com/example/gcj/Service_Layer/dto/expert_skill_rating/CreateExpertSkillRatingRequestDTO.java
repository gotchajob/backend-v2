package com.example.gcj.Service_Layer.dto.expert_skill_rating;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateExpertSkillRatingRequestDTO {
    private int rating;
    private long expertSkillOptionId;
}
