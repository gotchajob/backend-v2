package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback.BookingExpertFeedbackListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingExpertFeedback;

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
