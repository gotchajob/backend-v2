package com.example.gcj.Service_Layer.dto.expert_skill_option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ExpertSkillOptionResponseDTO implements Serializable {
    private long id;
    private long skillOptionId;
    private String skillOptionName;
    private long defaultPoint;
    private String certificate;
    private int status;
    private long sumPoint;
    private long totalRating;
}
