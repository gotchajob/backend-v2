package com.example.gcj.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateSkillRequestDTO {
    private long categoryId;
    private long skillId;
    private String skillName;
}
