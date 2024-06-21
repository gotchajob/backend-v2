package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.model.ExpertFormRequire;

public class ExpertFormRequireMapper {
    public static ExpertFormRequireResponseDTO toDto(ExpertFormRequire expertFormRequire) {
        if (expertFormRequire == null) {
            return null;
        }

        return ExpertFormRequireResponseDTO
                .builder()
                .id(expertFormRequire.getId())
                .name(expertFormRequire.getName())
                .description(expertFormRequire.getDescription())
                .categoryId(expertFormRequire.getCategoryId())
                .build();
    }
}
