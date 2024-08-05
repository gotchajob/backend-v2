package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.model.BookingExpertFeedbackAnswer;

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
