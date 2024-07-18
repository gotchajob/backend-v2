package com.example.gcj.dto.expert;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ExpertMatchListResponseDTO implements Serializable {
    private long userId;
    private long expertId;
    private String fullName;
    private String avatar;
    private String email;
    private int yearExperience;
    private String bio;
    List<ExpertSkillOptionResponseDTO> Skills;
    List<ExpertNationSupportResponseDTO> nationSupport;

    private double point;
}
