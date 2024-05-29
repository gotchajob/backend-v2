package com.example.gcj.dto.expert_skill_option;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateExpertSkillOptionPointRequestDTO implements Serializable {
    private long expertSkillOptionId;
    private int newDefaultPoint;
}
