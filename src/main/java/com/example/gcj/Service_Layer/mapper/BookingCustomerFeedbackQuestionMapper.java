package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackQuestion;

public class BookingCustomerFeedbackQuestionMapper {
    public static BookingCustomerFeedbackQuestionListResponseDTO toDto(BookingCustomerFeedbackQuestion bookingCustomerFeedbackQuestion) {
        if (bookingCustomerFeedbackQuestion == null) {
            return null;
        }

        return BookingCustomerFeedbackQuestionListResponseDTO
                .builder()
                .id(bookingCustomerFeedbackQuestion.getId())
                .question(bookingCustomerFeedbackQuestion.getQuestion())
                .type(bookingCustomerFeedbackQuestion.getType())
                .build();
    }
}
