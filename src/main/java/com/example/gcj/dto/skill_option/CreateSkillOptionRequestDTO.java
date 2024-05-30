package com.example.gcj.dto.skill_option;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSkillOptionRequestDTO {
    private long skillId;
    private String name;
}
