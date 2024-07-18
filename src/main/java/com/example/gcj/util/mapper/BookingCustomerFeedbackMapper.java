package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.model.BookingCustomerFeedback;

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
