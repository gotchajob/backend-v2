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
import com.example.gcj.Service_Layer.mapper.AvailabilityMapper;
import com.example.gcj.Shared.util.status.AvailabilityStatus;
import com.example.gcj.Shared.util.status.ExpertStatus;
import com.example.gcj.Shared.util.status.UserStatus;
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
            throw new CustomException("không tìm thấy chuyên gia");
        }
        long minusToCreateAvailability = policyService.getByKey(PolicyKey.MINUS_TO_CREATE_AVAILABILITY, Long.class);

        for (CreateAvailabilityRequestDTO r : request) {
            if (r.getStartTime().isAfter(r.getEndTime())) {
                throw new CustomException("thời gian bắt đầu phải nhỏ hơn thời gian kết thúc");
            }

            LocalDateTime start = r.getDate().atTime(r.getStartTime());
            LocalDateTime end = r.getDate().atTime(r.getEndTime());

            if (LocalDateTime.now().plusMinutes(minusToCreateAvailability).isAfter(start)) {
                throw new CustomException("phải tạo lich rảnh trước ngày, giờ phỏng vấn " + minusToCreateAvailability + " phút");
            }

            long durationMin = policyService.getByKey(PolicyKey.DURATION_BOOKING_MIN, Long.class);
            long durationMax = policyService.getByKey(PolicyKey.DURATION_BOOKING_MAX, Long.class);
            Duration duration = Duration.between(start, end);
            long durationMinus = duration.toMinutes();
            if (durationMinus < durationMin || durationMinus > durationMax) {
                throw new CustomException("thời gian phải trong khoảng từ " + durationMin + " đến " + durationMax + " phút!");
            }

            if (availabilityRepository.isOverlappingAvailabilities(expertId, r.getDate(), r.getStartTime(), r.getEndTime())) {
                throw new CustomException("trùng lặp với lich rảnh khác");
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
            throw new CustomException("không tìm thấy lich rảnh của chuyên gia");
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
        Expert expert = expertRepository.getById(expertId);
        if (expert == null) {
            throw new CustomException("không tìm thấy chuyên gia");
        }

        if (expert.getStatus() != ExpertStatus.BOOKING || expert.getUser().getStatus() != UserStatus.ACTIVE) {
            throw new CustomException("chuyên gia không nhận đặt lịch");
        }

        long minusToValidBooking = policyService.getByKey(PolicyKey.MINUS_TO_BOOKING, Long.class);
        LocalDateTime now = LocalDateTime.now().plusMinutes(minusToValidBooking);

        List<Availability> availabilities = availabilityRepository.getValidDate(expertId, now.toLocalDate(), now.toLocalTime());
        return availabilities.stream().map(AvailabilityMapper::toDto).toList();
    }

    @Override
    public boolean delete(long id, long expertId) {
        Availability availability = availabilityRepository.findById(id);
        if (availability == null) {
            throw new CustomException("không tìm thấy lich rảnh");
        }

        if (availability.getExpertId() != expertId) {
            throw new CustomException("lịch rảnh không phải của bạn");
        }
        if (availability.getStatus() == AvailabilityStatus.BOOKED) {
            throw new CustomException("không thể xóa khi lich rảnh đã được đặt");
        }

        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);
        return true;
    }
}
