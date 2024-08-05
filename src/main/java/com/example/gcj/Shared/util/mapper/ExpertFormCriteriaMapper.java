package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.expert_form_criteria.ExpertFormCriteriaResponseDTO;
import com.example.gcj.Repository_Layer.model.ExpertFormCriteria;

public class ExpertFormCriteriaMapper {
    public static ExpertFormCriteriaResponseDTO toDto(ExpertFormCriteria expertFormCriteria) {
        if (expertFormCriteria == null) {
            return null;
        }

        return ExpertFormCriteriaResponseDTO
                .builder()
                .id(expertFormCriteria.getId())
                .criteria(expertFormCriteria.getCriteria())
                .description(expertFormCriteria.getDescription())
                .status(expertFormCriteria.getStatus())
                .expertRegisterRequestId(expertFormCriteria.getExpertRequestId())
                .build();
    }
}
