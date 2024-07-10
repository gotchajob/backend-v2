package com.example.gcj.service.impl;

import com.example.gcj.dto.booking.*;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Booking;
import com.example.gcj.model.BookingSkill;
import com.example.gcj.repository.AvailabilityRepository;
import com.example.gcj.repository.BookingRepository;
import com.example.gcj.repository.BookingSkillRepository;
import com.example.gcj.service.BookingService;
import com.example.gcj.service.BookingSkillService;
import com.example.gcj.util.mapper.BookingMapper;
import com.example.gcj.util.status.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingSkillService bookingSkillService;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public void delete(long id) {
        updateStatus(id, BookingStatus.DELETE);
    }

    @Override
    public boolean update(long id, UpdateBookingRequestDTO request) {
        return false;
    }

    @Override
    public boolean create(long customerId, CreateBookingRequestDTO request) {
        if (request == null) {
            throw new CustomException("request is null");
        }

        if (!availabilityRepository.existsById(request.getAvailabilityId())) {
            throw new CustomException("not found availability with id " + request.getAvailabilityId());
        }

        //todo:check valid date

        Booking build = Booking
                .builder()
                .customerId(customerId)
                .expertId(request.getExpertId())
                .availabilityId(request.getAvailabilityId())
                .interviewDate(request.getInterviewDate())
                .startInterviewTime(request.getStartInterviewTime())
                .endInterviewTime(request.getEndInterviewTime())
                .note(request.getNote())
                .status(1)
                .oldBooking(null)
                .build();
        Booking save = bookingRepository.save(build);

        if (request.getBookingSkill() == null || request.getBookingSkill().isEmpty()) {
            return true;
        }

        bookingSkillService.add(request.getBookingSkill(), save.getId());
        return true;
    }

    @Override
    public List<BookingListResponseDTO> getAll() {
        List<Booking> bookingList = bookingRepository.findAll();

        return bookingList.stream().map(BookingMapper::toDto).toList();
    }

    @Override
    public List<BookingListResponseDTO> getByCurrent(long customerId) {
        List<Booking> bookingList = bookingRepository.getByCustomerId(customerId);

        return bookingList.stream().map(BookingMapper::toDto).toList();
    }

    @Override
    public BookingResponseDTO getById(long id) {
        Booking booking = get(id);

        return BookingResponseDTO
                .builder()
                .id(booking.getId())
                .expertId(booking.getExpertId())
                .customerId(booking.getCustomerId())
                .startInterviewDate(booking.getInterviewDate().atTime(booking.getStartInterviewTime()))
                .endInterviewDate(booking.getInterviewDate().atTime(booking.getEndInterviewTime()))
                .note(booking.getNote())
                .rejectReason(booking.getRejectReason())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .expertSkillOptionIds(booking.getBookingSkills().stream().map(BookingSkill::getExpertSkillOptionId).toList())
                .build();
    }

    @Override
    public boolean updateStatus(long id, int status) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("not found booking with id " + id);
        }

        booking.setStatus(status);
        bookingRepository.save(booking);

        return true;
    }

    @Override
    public boolean approve(long expertId, long id) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("current expert not same with expert in booking");
        }

        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT) {
            throw new CustomException("booking is not wait to expert accept");
        }

        LocalDateTime bookingDate = booking.getInterviewDate().atTime(booking.getStartInterviewTime());
        if (bookingDate.isBefore(LocalDateTime.now())) {
            throw new CustomException("interview date is after now");
        }

        booking.setStatus(BookingStatus.WAIT_TO_INTERVIEW);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean reject(long expertId, long id, RejectBookingRequestDTO request) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("current expert not same with expert in booking");
        }

        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT) {
            throw new CustomException("booking is not wait to expert accept");
        }

        booking.setStatus(BookingStatus.CANCEL_BY_EXPERT);
        booking.setRejectReason(request.getReason());
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean cancel(long customerId, long id) {
        Booking booking = get(id);
        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer not same with customer in booking");
        }

        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT && booking.getStatus() != BookingStatus.WAIT_TO_INTERVIEW) {
            throw new CustomException("current status is not wait expert accept or wait to interview");
        }

        if (booking.getStatus() == BookingStatus.WAIT_TO_INTERVIEW) {
            int dayBeforeCancelBooking = 3;//todo: load from policy

            LocalDateTime bookingStartDate = booking.getInterviewDate().atTime(booking.getStartInterviewTime());

            LocalDateTime validCancelBookingDate = LocalDateTime.now().plusDays(dayBeforeCancelBooking);
            if (!validCancelBookingDate.isBefore(bookingStartDate)) {
                throw new CustomException("must cancel at least " + dayBeforeCancelBooking + " days before booking interview start");
            }
        }

        //todo: check when it cancel
        booking.setStatus(BookingStatus.CANCEl_BY_CUSTOMER);
        bookingRepository.save(booking);
        return true;
    }

    private Booking get(long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("not found booking with id " + id);
        }

        return booking;
    }
}
