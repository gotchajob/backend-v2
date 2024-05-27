package com.example.gcj.util.mapper;

import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.model.SkillOption;

public class SkillOptionMapper {

    public static SkillOptionResponseDTO toDto(SkillOption skill) {
        return SkillOptionResponseDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
