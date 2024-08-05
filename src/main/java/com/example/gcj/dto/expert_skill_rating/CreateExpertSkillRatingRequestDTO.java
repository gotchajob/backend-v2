package com.example.gcj.dto.expert_skill_rating;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateExpertSkillRatingRequestDTO {
    private int rating;
    private long expertSkillOptionId;
}
