package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Availability;
import com.example.gcj.Repository_Layer.model.Expert;
import com.example.gcj.Repository_Layer.repository.AvailabilityRepository;
import com.example.gcj.Repository_Layer.repository.ExpertRepository;
import com.example.gcj.Service_Layer.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.CreateAvailabilityRequestDTO;
import com.example.gcj.Service_Layer.service.AvailabilityService;
import com.example.gcj.Service_Layer.service.PolicyService;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.mapper.AvailabilityMapper;
import com.example.gcj.Shared.util.status.AvailabilityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
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
        long minusToCreateAvailability = policyService.getByKey(PolicyKey.MINUS_TO_CREATE_AVAILABILITY, Long.class);

        for (CreateAvailabilityRequestDTO r : request) {
            if (r.getStartTime().isAfter(r.getEndTime())) {
                throw new CustomException("end time must larger startTime. value: " + r.toString());
            }

            LocalDateTime start = r.getDate().atTime(r.getStartTime());
            LocalDateTime end = r.getDate().atTime(r.getEndTime());

            if (LocalDateTime.now().plusMinutes(minusToCreateAvailability).isAfter(start)) {
                throw new CustomException("must be created " + minusToCreateAvailability + " minus before the interview begins");
            }

            long durationMin = policyService.getByKey(PolicyKey.DURATION_BOOKING_MIN, Long.class);
            long durationMax = policyService.getByKey(PolicyKey.DURATION_BOOKING_MAX, Long.class);
            Duration duration = Duration.between(start, end);
            long durationMinus = duration.toMinutes();
            if (durationMinus < durationMin || durationMinus > durationMax) {
                throw new CustomException("duration must in " + durationMin + " to " + durationMax + " minus!");
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
        long minusToValidBooking = policyService.getByKey(PolicyKey.MINUS_TO_VALID_AVAILABILITY, Long.class);
        LocalDateTime now = LocalDateTime.now().plusMinutes(minusToValidBooking);

        List<Availability> availabilities = availabilityRepository.getValidDate(expertId, now.toLocalDate(), now.toLocalTime());
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
        if (availability.getStatus() == AvailabilityStatus.BOOKED) {
            throw new CustomException("cannot delete when availability is booked");
        }

        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);
        return true;
    }
}
