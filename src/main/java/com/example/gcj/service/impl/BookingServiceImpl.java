package com.example.gcj.service.impl;

import com.example.gcj.dto.booking.*;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.*;
import com.example.gcj.repository.*;
import com.example.gcj.service.BookingService;
import com.example.gcj.service.BookingSkillService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.mapper.BookingMapper;
import com.example.gcj.util.status.AvailabilityStatus;
import com.example.gcj.util.status.BookingStatus;
import com.example.gcj.util.status.ExpertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final CvRepository cvRepository;
    private final ExpertRepository expertRepository;
    private final BookingSkillService bookingSkillService;
    private final AvailabilityRepository availabilityRepository;
    private final CustomerRepository customerRepository;
    private final PolicyService policyService;

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

        Expert expert = expertRepository.getById(request.getExpertId());
        if (expert == null) {
            throw new CustomException("not found expert with expert id "  + request.getExpertId());
        }
        if (expert.getStatus() != ExpertStatus.BOOKING) {
            throw new CustomException("expert is not order to booking");
        }


        int dayToBooking = policyService.getByKey(PolicyKey.DAY_TO_BOOKING, Integer.class);
        Availability availability = availabilityRepository.findById(request.getAvailabilityId());
        if (availability == null) {
            throw new CustomException("not found availability with id " + request.getAvailabilityId());
        }
        if (availability.getStatus() != AvailabilityStatus.VALID) {
            throw new CustomException("invalid availability. current status is " + availability.getStatus());
        }
        
        if (availability.getExpertId() != request.getExpertId()) {
            throw new CustomException("expert in availability not same with expert in request");
        }
        
        LocalDateTime interviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
        if (LocalDateTime.now().plusDays(dayToBooking).isAfter(interviewTime)) {
            throw new CustomException("booking need at least " + dayToBooking + " days before interview start");
        }
        
        availability.setStatus(AvailabilityStatus.BOOKED);
        availabilityRepository.save(availability);

        Cv cv = cvRepository.getById(request.getCustomerCvId());
        if (cv == null) {
            throw new CustomException("not found cv with cv id " + cv.getId());
        }

        if (cv.getCustomerId() != customerId) {
            throw new CustomException("current customer id not same with customer id in cv");
        }

        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new CustomException("not found customer with customer id " + customerId);
        }
        if (customer.getNumberBooking() < 1) {
            throw new CustomException("not already purchased booking service");
        }
        customer.setNumberBooking(customer.getNumberBooking() - 1);
        customerRepository.save(customer);

        Booking build = Booking
                .builder()
                .customerId(customerId)
                .expertId(request.getExpertId())
                .availability(Availability.builder().id(request.getAvailabilityId()).build())
                .customerCvId(request.getCustomerCvId())
                .note(request.getNote())
                .status(BookingStatus.WAIT_EXPERT_ACCEPT)
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
        List<Booking> bookings = bookingRepository.findAll();

        int dayToCancelBooking = getDayToCancel();
        return bookings.stream().map(b -> BookingMapper.toDto(b, dayToCancelBooking)).toList();
    }

    @Override
    public List<BookingListResponseDTO> getByCurrent(long customerId) {
        List<Booking> bookings = bookingRepository.getByCustomerId(customerId);

        int dayToCancelBooking = getDayToCancel();
        return bookings.stream().map(b -> BookingMapper.toDto(b, dayToCancelBooking)).toList();
    }

    @Override
    public List<BookingListResponseDTO> getByCurrentAndStatus(long customerId, Integer status) {
        List<Booking> bookings = status == null ? bookingRepository.getByCustomerId(customerId)
                : bookingRepository.getByCustomerIdAndStatus(customerId, status);

        int dayToCancelBooking = getDayToCancel();
        return bookings.stream().map(b -> BookingMapper.toDto(b, dayToCancelBooking)).toList();
    }

    @Override
    public BookingResponseDTO getById(long id) {
        Booking booking = get(id);
        Availability availability = booking.getAvailability();
        return BookingResponseDTO
                .builder()
                .id(booking.getId())
                .expertId(booking.getExpertId())
                .customerId(booking.getCustomerId())
                .startInterviewDate(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endInterviewDate(availability.getAvailableDate().atTime(availability.getEndTime()))
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
        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("not found availability");
        }

        long dayBeforeToApprove = 1;
        LocalDateTime interviewTime = availability.getAvailableDate().atTime(availability.getStartTime());
        if (LocalDateTime.now().plusDays(dayBeforeToApprove).isAfter(interviewTime)) {
            throw new CustomException("need to approve before " + dayBeforeToApprove + (dayBeforeToApprove > 1 ? " day" : " days") +  " of interview day");
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

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("availability is null");
        }

        availability.setStatus(AvailabilityStatus.VALID);
        availabilityRepository.save(availability);

        return true;
    }

    @Override
    public List<BookingListResponseDTO> getByExpertId(long expertId) {
        List<Booking> bookings = bookingRepository.getByExpertId(expertId);
        int dayToCancelBooking = getDayToCancel();
        return bookings.stream().map(b -> BookingMapper.toDto(b, dayToCancelBooking)).toList();
    }

    @Override
    public List<BookingListResponseDTO> getByExpertIdAndStatus(long expertId, Integer status) {
        List<Booking> bookings = status == null
                ? bookingRepository.getByExpertId(expertId)
                : bookingRepository.getByExpertIdAndStatus(expertId, status);
        int dayToCancelBooking = getDayToCancel();
        return bookings.stream().map(b -> BookingMapper.toDto(b, dayToCancelBooking)).toList();
    }

    @Override
    public boolean cancel(long customerId, long id) {
        Booking booking = get(id);
        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer not same with customer in booking");
        }
        Availability availability = checkBookingToCancelAndGetAvailability(booking);

        LocalDateTime interviewDay = availability.getAvailableDate().atTime(availability.getStartTime());
        if (!canCancelBooking(interviewDay)) {
            throw new CustomException("too late to cancel");
        }
        availability.setStatus(AvailabilityStatus.VALID);
        availabilityRepository.save(availability);

        booking.setStatus(BookingStatus.CANCEl_BY_CUSTOMER);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean cancelByExpert(long expertId, long id) {
        Booking booking = get(id);
        if (booking.getExpertId() != expertId) {
            throw new CustomException("current customer not same with customer in booking");
        }

        if (booking.getStatus() != BookingStatus.WAIT_TO_INTERVIEW) {
            throw new CustomException("current status is not wait to interview");
        }

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("not found availability");
        }

        LocalDateTime interviewDay = getInterviewDay(availability.getAvailableDate(), availability.getStartTime());
        if (!canCancelBooking(interviewDay)) {
            throw new CustomException("too late to cancel");
        }
        availability.setStatus(AvailabilityStatus.DELETE);
        availabilityRepository.save(availability);

        booking.setStatus(BookingStatus.CANCEL_BY_EXPERT);
        bookingRepository.save(booking);
        return true;
    }

    private Availability checkBookingToCancelAndGetAvailability(Booking booking) {
        if (booking.getStatus() != BookingStatus.WAIT_EXPERT_ACCEPT && booking.getStatus() != BookingStatus.WAIT_TO_INTERVIEW) {
            throw new CustomException("current status is not wait expert accept or wait to interview");
        }

        Availability availability = booking.getAvailability();
        if (availability == null) {
            throw new CustomException("not found availability");
        }

        return availability;
    }

    private LocalDateTime getInterviewDay(LocalDate date, LocalTime time) {
        return date.atTime(time);
    }

    private Booking get(long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new CustomException("not found booking with id " + id);
        }

        return booking;
    }

    private int getDayToCancel() {
        return policyService.getByKey(PolicyKey.DAY_TO_CANCEL_BOOKING, Integer.class);
    }

    private boolean canCancelBooking(LocalDateTime startDateTime) {
        int dayCancelBooking = getDayToCancel();
        return LocalDateTime.now().plusDays(dayCancelBooking).isBefore(startDateTime);
    }
}
