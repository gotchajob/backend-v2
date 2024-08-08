package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.booking.BookingListResponseDTO;
import com.example.gcj.Repository_Layer.model.Availability;
import com.example.gcj.Repository_Layer.model.Booking;

import java.time.LocalDateTime;

public class BookingMapper {
    public static BookingListResponseDTO toDto(Booking booking, final long minusToCancel) {
        if (booking == null) {
            return null;
        }

        Availability availability = booking.getAvailability();
        boolean canCancel = (booking.getStatus() == 1 || booking.getStatus() == 2)
                && (LocalDateTime.now().plusMinutes(minusToCancel).isBefore(availability.getAvailableDate().atTime(availability.getStartTime())));

        return BookingListResponseDTO
                .builder()
                .id(booking.getId())
                .customerId(booking.getCustomerId())
                .expertId(booking.getExpertId())
                .startInterviewDate(availability.getAvailableDate().atTime(availability.getStartTime()))
                .endInterviewDate(availability.getAvailableDate().atTime(availability.getEndTime()))
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .canCancel(canCancel)
                .build();
    }
}
