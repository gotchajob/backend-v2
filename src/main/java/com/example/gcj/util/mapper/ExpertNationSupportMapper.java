package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.model.ExpertNationSupport;

public class ExpertNationSupportMapper {
    public static ExpertNationSupportResponseDTO toDto(ExpertNationSupport expertNationSupport) {
        return ExpertNationSupportResponseDTO
                .builder()
                .id(expertNationSupport.getId())
                .nation(expertNationSupport.getNation())
                .build();
    }
}
