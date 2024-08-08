package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.Repository_Layer.model.Availability;

public class AvailabilityMapper {
    public static AvailabilityListResponseDTO toDto(Availability availability) {
        if (availability == null) return null;

        return AvailabilityListResponseDTO
                .builder()
                .id(availability.getId())
                .expertId(availability.getExpertId())
                .status(availability.getStatus())
                .startTime(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endTime(availability.getAvailableDate().atTime(availability.getEndTime()))
                .build();
    }
}
