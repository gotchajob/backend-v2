package com.example.gcj.Service_Layer.dto.expert_skill_option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CreateExpertSkillOptionDTO implements Serializable {
    private long skillOptionId;
    private String certificate;
}
