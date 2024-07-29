package com.example.gcj.service.impl;

import com.example.gcj.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.dto.availability.CreateAvailabilityRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Availability;
import com.example.gcj.model.Expert;
import com.example.gcj.repository.AvailabilityRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.service.AvailabilityService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.mapper.AvailabilityMapper;
import com.example.gcj.util.status.AvailabilityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ExpertRepository expertRepository;
    private final PolicyService policyService;

    @Override
    public boolean create(long expertId, List<CreateAvailabilityRequestDTO> request) {
        Expert expert = expertRepository.getById(expertId);
        if (expert == null) {
            throw new CustomException("not found expert with id " + expertId);
        }
        int dayToCreateAvailability = 5;

        for (CreateAvailabilityRequestDTO r : request) {
            if (r.getStartTime().isAfter(r.getEndTime())) {
                throw new CustomException("end time must larger startTime. value: " + r.toString());
            }
            //todo: check duration time

            LocalDateTime interviewDay = r.getDate().atTime(r.getStartTime());
            if (LocalDateTime.now().plusDays(dayToCreateAvailability).isAfter(interviewDay)) {
                throw new CustomException("must be created " + dayToCreateAvailability + " days before the interview begins");
            }

            if (availabilityRepository.isOverlappingAvailabilities(expertId, r.getDate(), r.getStartTime(), r.getEndTime())) {
                throw new CustomException("overlapping availability. value: " + r.toString());
            }

            Availability build = Availability
                    .builder()
                    .expertId(expertId)
                    .availableDate(r.getDate())
                    .startTime(r.getStartTime())
                    .endTime(r.getEndTime())
                    .status(AvailabilityStatus.VALID)
                    .build();
            availabilityRepository.save(build);
        }

        return true;
    }

    @Override
    public AvailabilityResponseDTO getById(long id) {
        Availability availability = availabilityRepository.findById(id);
        if (availability == null) {
            throw new CustomException("not found availability with id " + id);
        }

        return AvailabilityResponseDTO
                .builder()
                .id(availability.getId())
                .startTime(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endTime(availability.getAvailableDate().atTime(availability.getEndTime()))
                .build();
    }

    @Override
    public List<AvailabilityListResponseDTO> get(Long expertId) {
        List<Availability> availabilityList = expertId == null
                ? availabilityRepository.findAll()
                : availabilityRepository.getByExpertId(expertId);
        return availabilityList.stream().map(AvailabilityMapper::toDto).toList();
    }

    @Override
    public List<AvailabilityListResponseDTO> getValidDateToBooking(long expertId) {
        int dayToValidBooking = 4;//todo: load from policy
        LocalDate validDate = LocalDate.now().plusDays(dayToValidBooking);
        LocalTime currentTime = LocalTime.now();

        List<Availability> availabilities = availabilityRepository.getValidDate(expertId, validDate, currentTime);
        return availabilities.stream().map(AvailabilityMapper::toDto).toList();
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

        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);
        return true;
    }
}
