package com.example.gcj.Service_Layer.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillResponseDTO {
    private long categoryId;
    private long id;
    private String name;
}
