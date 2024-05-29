package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.model.ExpertSkillOption;

public class ExpertSkillOptionMapper {
    public static ExpertSkillOptionResponseDTO toDto(ExpertSkillOption expertSkillOption) {
        return ExpertSkillOptionResponseDTO
                .builder()
                .id(expertSkillOption.getId())
                .skillOptionId(expertSkillOption.getSkillOptionId())
                .skillOptionName("coming soon!")
                .defaultPoint(expertSkillOption.getDefaultPoint())
                .certificate(expertSkillOption.getCertification())
                .build();
    }
}
