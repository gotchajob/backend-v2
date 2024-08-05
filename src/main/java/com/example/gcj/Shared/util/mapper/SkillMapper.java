package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Repository_Layer.model.Skill;

public class SkillMapper {

    public static SkillResponseDTO toDto(Skill skill) {
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .categoryId(skill.getCategoryId())
                .name(skill.getName())
                .build();
    }
}
