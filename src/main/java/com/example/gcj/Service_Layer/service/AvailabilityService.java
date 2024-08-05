package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.CreateAvailabilityRequestDTO;

import java.util.List;

public interface AvailabilityService {
    boolean create(long expertId, List<CreateAvailabilityRequestDTO> request);

    AvailabilityResponseDTO getById(long id);

    List<AvailabilityListResponseDTO> get(Long expertId);
    List<AvailabilityListResponseDTO> getValidDateToBooking(long expertId);

    boolean delete(long id, long expertId);
}
