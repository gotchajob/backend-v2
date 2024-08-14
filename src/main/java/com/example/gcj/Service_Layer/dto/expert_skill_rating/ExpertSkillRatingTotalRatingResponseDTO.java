package com.example.gcj.Service_Layer.dto.expert_skill_rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ExpertSkillRatingTotalRatingResponseDTO {
    private int rating;
    private long count;
}
