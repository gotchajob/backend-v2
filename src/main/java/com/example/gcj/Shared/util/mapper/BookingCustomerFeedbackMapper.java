package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedback;

public class BookingCustomerFeedbackMapper {
    public static BookingCustomerFeedbackListResponseDTO toDto(BookingCustomerFeedback bookingCustomerFeedback) {
        if (bookingCustomerFeedback == null) {
            return null;
        }

        return BookingCustomerFeedbackListResponseDTO
                .builder()
                .id(bookingCustomerFeedback.getId())
                .rating(bookingCustomerFeedback.getRating())
                .bookingId(bookingCustomerFeedback.getBookingId())
                .comment(bookingCustomerFeedback.getComment())
                .build();
    }
}
