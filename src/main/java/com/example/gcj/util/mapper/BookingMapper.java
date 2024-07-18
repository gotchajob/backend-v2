package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking.BookingListResponseDTO;
import com.example.gcj.model.Availability;
import com.example.gcj.model.Booking;
import com.example.gcj.model.BookingSkill;

import java.time.LocalDateTime;

public class BookingMapper {
    public static BookingListResponseDTO toDto(Booking booking, final int dayToCancel) {
        if (booking == null) {
            return null;
        }

        Availability availability = booking.getAvailability();
        boolean canCancel = (booking.getStatus() == 1 || booking.getStatus() == 2)
                && (LocalDateTime.now().plusDays(dayToCancel).isBefore(availability.getAvailableDate().atTime(availability.getStartTime())));

        return BookingListResponseDTO
                .builder()
                .id(booking.getId())
                .customerId(booking.getCustomerId())
                .expertId(booking.getExpertId())
                .availabilityId(availability.getId())
                .startInterviewDate(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endInterviewDate(availability.getAvailableDate().atTime(availability.getStartTime()))
                .status(booking.getStatus())
                .note(booking.getNote())
                .customerCvId(booking.getCustomerCvId())
                .rejectReason(booking.getRejectReason())
                .createdAt(booking.getCreatedAt())
                .expertSkillOptionId(booking.getBookingSkills().stream().map(BookingSkill::getExpertSkillOptionId).toList())
                .canCancel(canCancel)
                .build();
    }
}
