package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.Repository_Layer.model.ExpertFormRequire;

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
