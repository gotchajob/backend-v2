package com.example.gcj.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillResponseDTO {
    public long categoryId;
    public long id;
    public String name;
}
