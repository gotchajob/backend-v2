package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackAnswer;

public class BookingExpertFeedbackAnswerMapper {
    public static BookingExpertFeedbackAnswerListResponseDTO toDto(BookingExpertFeedbackAnswer b) {
        if (b == null) {
            return null;
        }

        return BookingExpertFeedbackAnswerListResponseDTO
                .builder()
                .id(b.getId())
                .questionId(b.getQuestion().getId())
                .question(b.getQuestion().getQuestion())
                .questionType(b.getQuestion().getType())
                .categoryId(b.getQuestion().getCategory().getId())
                .category(b.getQuestion().getCategory().getCategory())
                .answer(b.getAnswer())
                .build();
    }
}
