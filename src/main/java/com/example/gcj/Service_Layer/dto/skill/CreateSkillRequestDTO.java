package com.example.gcj.Service_Layer.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSkillRequestDTO {
    private long categoryId;
    private String name;
}
