package com.example.gcj.dto.booking_customer_feedback_answer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCustomerFeedbackAnswerListResponseDTO {
    private long id;
    private long questionId;
    private String question;
    private String questionType;
    private String answer;
}
