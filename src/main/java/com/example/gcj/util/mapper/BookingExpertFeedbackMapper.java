package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_expert_feedback.BookingExpertFeedbackListResponseDTO;
import com.example.gcj.model.BookingExpertFeedback;

public class BookingExpertFeedbackMapper {
    public static BookingExpertFeedbackListResponseDTO toDto(BookingExpertFeedback bookingExpertFeedback) {
        if (bookingExpertFeedback == null) {
            return null;
        }

        return BookingExpertFeedbackListResponseDTO
                .builder()
                .id(bookingExpertFeedback.getId())
                .comment(bookingExpertFeedback.getComment())
                .build();
    }
}
