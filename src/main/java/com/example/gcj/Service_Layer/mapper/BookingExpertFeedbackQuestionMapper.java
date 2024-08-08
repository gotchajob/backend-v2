package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackQuestion;
import com.example.gcj.Repository_Layer.model.ExpertQuestionCategory;

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
