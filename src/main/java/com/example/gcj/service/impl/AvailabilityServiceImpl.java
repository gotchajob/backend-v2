package com.example.gcj.service.impl;

import com.example.gcj.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.dto.availability.CreateAvailabilityRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Availability;
import com.example.gcj.repository.AvailabilityRepository;
import com.example.gcj.service.AvailabilityService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.AvailabilityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    @Override
    public boolean create(long expertId, List<CreateAvailabilityRequestDTO> request) {
        //todo check expert id

        List<Availability> availabilities = new ArrayList<>();

        for (CreateAvailabilityRequestDTO r : request) {
            //todo: check time is valid

            Availability build = Availability
                    .builder()
                    .expertId(expertId)
                    .availableDate(r.getDate())
                    .startTime(r.getStartTime())
                    .endTime(r.getEndTime())
                    .status(1)
                    .build();
            availabilities.add(build);
        }

        if (availabilities.isEmpty()) {
            return false;
        }

        availabilityRepository.saveAll(availabilities);

        return true;
    }

    @Override
    public AvailabilityResponseDTO getById(long id) {
        Availability availability = availabilityRepository.findById(id);
        if (availability == null) {
            throw new CustomException("not found availability with id " + id);
        }

        //todo: finish this
        return AvailabilityResponseDTO.builder().build();
    }

    @Override
    public List<AvailabilityListResponseDTO> get(Long expertId) {
        List<Availability> availabilityList = expertId == null
                ? availabilityRepository.findAll()
                : availabilityRepository.getByExpertIdAndStatus(expertId, 1);
        return availabilityList.stream().map(AvailabilityMapper::toDto).toList();
    }

    @Override
    public boolean delete(long id, long expertId) {
        Availability availability = availabilityRepository.findById(id);
        if (availability == null) {
            throw new CustomException("not found availability with id " + id);
        }

        if (availability.getExpertId() != expertId) {
            throw new CustomException("expert availability not same with current expert");
        }

        availability.setStatus(0);
        availabilityRepository.save(availability);
        return true;
    }
}
