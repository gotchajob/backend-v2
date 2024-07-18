package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.model.BookingCustomerFeedbackQuestion;
import com.example.gcj.model.BookingExpertFeedbackQuestion;

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
