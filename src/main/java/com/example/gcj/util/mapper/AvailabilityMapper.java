package com.example.gcj.util.mapper;

import com.example.gcj.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.model.Availability;

public class AvailabilityMapper {
    public static AvailabilityListResponseDTO toDto(Availability availability) {
        if (availability == null) return null;

        return AvailabilityListResponseDTO
                .builder()
                .id(availability.getId())
                .expertId(availability.getExpertId())
                .startTime(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endTime(availability.getAvailableDate().atTime(availability.getEndTime()))
                .build();
    }
}
