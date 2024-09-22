package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.policy.PolicyListResponseDTO;
import com.example.gcj.Service_Layer.dto.policy.PolicyResponseDTO;
import com.example.gcj.Repository_Layer.model.Policy;

public class PolicyMapper {
    public static PolicyListResponseDTO toDto(Policy policy) {
        if (policy == null) {
            return null;
        }

        return PolicyListResponseDTO
                .builder()
                .id(policy.getId())
                .key(policy.getKey())
                .value(policy.getValue())
                .description(policy.getDescription())
                .build();
    }

    public static PolicyResponseDTO toDtoDetail(Policy policy) {
        if (policy == null) {
            return null;
        }

        return PolicyResponseDTO
                .builder()
                .id(policy.getId())
                .key(policy.getKey())
                .value(policy.getValue())
                .description(policy.getDescription())
                .build();
    }

}
