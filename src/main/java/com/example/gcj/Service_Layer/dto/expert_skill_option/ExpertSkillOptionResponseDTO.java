package com.example.gcj.Service_Layer.dto.expert_skill_option;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ExpertSkillOptionResponseDTO implements Serializable {
    private long id;
    private long skillOptionId;
    private String skillOptionName;
    private long defaultPoint;
    private String certificate;
    private int status;
}
