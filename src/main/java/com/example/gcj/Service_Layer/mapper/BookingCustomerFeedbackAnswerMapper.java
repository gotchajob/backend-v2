package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackAnswer;

public class BookingCustomerFeedbackAnswerMapper {
    public static BookingCustomerFeedbackAnswerListResponseDTO toDto(BookingCustomerFeedbackAnswer bookingCustomerFeedbackAnswer) {
        if (bookingCustomerFeedbackAnswer == null) {
            return null;
        }

        return BookingCustomerFeedbackAnswerListResponseDTO
                .builder()
                .id(bookingCustomerFeedbackAnswer.getId())
                .questionId(bookingCustomerFeedbackAnswer.getQuestion().getId())
                .question(bookingCustomerFeedbackAnswer.getQuestion().getQuestion())
                .questionType(bookingCustomerFeedbackAnswer.getQuestion().getType())
                .answer(bookingCustomerFeedbackAnswer.getAnswer())
                .build();
    }
}
