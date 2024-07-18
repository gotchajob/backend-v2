package com.example.gcj.util.mapper;

import com.example.gcj.dto.policy.PolicyListResponseDTO;
import com.example.gcj.dto.policy.PolicyResponseDTO;
import com.example.gcj.model.Policy;

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
