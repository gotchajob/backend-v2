package com.example.gcj.util.mapper;

import com.example.gcj.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.model.BookingCustomerFeedbackAnswer;

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
                .answer(bookingCustomerFeedbackAnswer.getAnswer())
                .build();
    }
}
