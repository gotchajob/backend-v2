package com.example.gcj.util.mapper;

import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.model.Skill;

public class SkillMapper {

    public static SkillResponseDTO toDto(Skill skill) {
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .categoryId(skill.getCategoryId())
                .name(skill.getName())
                .build();
    }
}
