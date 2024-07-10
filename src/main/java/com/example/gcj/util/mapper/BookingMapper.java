package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking.BookingListResponseDTO;
import com.example.gcj.model.Booking;
import com.example.gcj.model.BookingSkill;

public class BookingMapper {
    public static BookingListResponseDTO toDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        return BookingListResponseDTO
                .builder()
                .id(booking.getId())
                .customerId(booking.getCustomerId())
                .expertId(booking.getExpertId())
                .availabilityId(booking.getAvailabilityId())
                .startInterviewDate(booking.getInterviewDate().atTime(booking.getStartInterviewTime()))
                .endInterviewDate(booking.getInterviewDate().atTime(booking.getEndInterviewTime()))
                .status(booking.getStatus())
                .note(booking.getNote())
                .rejectReason(booking.getRejectReason())
                .createdAt(booking.getCreatedAt())
                .expertSkillOptionId(booking.getBookingSkills().stream().map(BookingSkill::getExpertSkillOptionId).toList())
                .build();
    }
}
