package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.Repository_Layer.model.ExpertSkillOption;

import java.util.Objects;

public class ExpertSkillOptionMapper {
    public static ExpertSkillOptionResponseDTO toDto(ExpertSkillOption expertSkillOption) {
        return ExpertSkillOptionResponseDTO
                .builder()
                .id(expertSkillOption.getId())
                .skillOptionId(expertSkillOption.getSkillOption().getId())
                .skillOptionName(expertSkillOption.getSkillOption().getName())
                .defaultPoint(expertSkillOption.getDefaultPoint())
                .certificate(expertSkillOption.getCertification())
                .build();
    }

    public static ExpertSkillOptionResponseDTO toDto(Object[] object) {
        if (object == null || object.length < 3) {
            return null;
        }

        ExpertSkillOption expertSkillOption = (ExpertSkillOption) object[0];
        Long sumPoints = Objects.requireNonNullElse((Long) object[1], 0L);
        Long ratingCount = Objects.requireNonNullElse((Long) object[2], 0L);

        return ExpertSkillOptionResponseDTO
                .builder()
                .id(expertSkillOption.getId())
                .skillOptionId(expertSkillOption.getSkillOption().getId())
                .skillOptionName(expertSkillOption.getSkillOption().getName())
                .defaultPoint(expertSkillOption.getDefaultPoint())
                .certificate(expertSkillOption.getCertification())
                .sumPoint(sumPoints)
                .totalRating(ratingCount)
                .build();
    }
}
