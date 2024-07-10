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
                .date(availability.getAvailableDate())
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .build();
    }
}
