package com.example.gcj.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSkillRequestDTO {
    public long categoryId;
    public String name;
}
