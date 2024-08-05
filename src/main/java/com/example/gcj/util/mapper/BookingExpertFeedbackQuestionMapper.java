package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.model.BookingExpertFeedbackQuestion;
import com.example.gcj.model.ExpertQuestionCategory;

public class BookingExpertFeedbackQuestionMapper {
    public static BookingExpertFeedbackQuestionListResponseDTO toDto(BookingExpertFeedbackQuestion bookingExpertFeedbackQuestion) {
        if (bookingExpertFeedbackQuestion == null) {
            return null;
        }
        ExpertQuestionCategory category = bookingExpertFeedbackQuestion.getCategory();
        if (category == null) {
            return null;
        }

        return BookingExpertFeedbackQuestionListResponseDTO
                .builder()
                .id(bookingExpertFeedbackQuestion.getId())
                .question(bookingExpertFeedbackQuestion.getQuestion())
                .type(bookingExpertFeedbackQuestion.getType())
                .categoryId(category.getId())
                .category(category.getCategory())
                .build();
    }
}
