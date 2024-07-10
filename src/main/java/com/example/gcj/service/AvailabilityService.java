package com.example.gcj.service;

import com.example.gcj.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.dto.availability.CreateAvailabilityRequestDTO;

import java.util.List;

public interface AvailabilityService {
    boolean create(long expertId, List<CreateAvailabilityRequestDTO> request);

    AvailabilityResponseDTO getById(long id);

    List<AvailabilityListResponseDTO> get(Long expertId);

    boolean delete(long id, long expertId);
}
