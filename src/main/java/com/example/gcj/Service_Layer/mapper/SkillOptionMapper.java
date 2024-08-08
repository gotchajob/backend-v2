package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.Repository_Layer.model.SkillOption;

public class SkillOptionMapper {

    public static SkillOptionResponseDTO toDto(SkillOption skill) {
        return SkillOptionResponseDTO.builder()
                .id(skill.getId())
                .skillId(skill.getSkillId())
                .name(skill.getName())
                .build();
    }
}
