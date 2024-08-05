package com.example.gcj.Service_Layer.dto.expert_skill_option;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateExpertSkillOptionPointRequestDTO implements Serializable {
    private long expertSkillOptionId;
    private int newDefaultPoint;
}
