package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Repository_Layer.model.ExpertNationSupport;

public class ExpertNationSupportMapper {
    public static ExpertNationSupportResponseDTO toDto(ExpertNationSupport expertNationSupport) {
        return ExpertNationSupportResponseDTO
                .builder()
                .id(expertNationSupport.getId())
                .nation(expertNationSupport.getNation())
                .build();
    }
}
