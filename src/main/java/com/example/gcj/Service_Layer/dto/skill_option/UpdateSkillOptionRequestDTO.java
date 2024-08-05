package com.example.gcj.Service_Layer.dto.skill_option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateSkillOptionRequestDTO {
    private long id;
    private long skillId;
    private String name;
}
