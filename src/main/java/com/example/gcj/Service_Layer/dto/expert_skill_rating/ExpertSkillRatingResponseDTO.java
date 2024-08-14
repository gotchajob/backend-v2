package com.example.gcj.Service_Layer.dto.expert_skill_rating;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ExpertSkillRatingResponseDTO {
    private long id;
    private long expertSkillOptionId;
    private int rating;
    private String comment;
    private Date createdAt;
}
