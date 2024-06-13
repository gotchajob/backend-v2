package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.model.ExpertRegisterRequest;

public class ExpertRegisterRequestMapper {
    public static ExpertRegisterRequestResponseDTO toDto(ExpertRegisterRequest expertRegisterRequest) {
        if (expertRegisterRequest == null) {
            return null;
        }

        return ExpertRegisterRequestResponseDTO
                .builder()
                .id(expertRegisterRequest.getId())
                .email(expertRegisterRequest.getEmail())
                .url(expertRegisterRequest.getUrl())
                .status(expertRegisterRequest.getStatus())
                .note(expertRegisterRequest.getNote())
                .expertId(expertRegisterRequest.getExpertId())
                .createdAt(expertRegisterRequest.getCreatedAt())
                .updatedAt(expertRegisterRequest.getUpdatedAt())
                .build();
    }
}
