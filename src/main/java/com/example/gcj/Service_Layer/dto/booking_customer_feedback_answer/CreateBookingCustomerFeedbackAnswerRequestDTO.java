package com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBookingCustomerFeedbackAnswerRequestDTO {
    private long questionId;
    private String answer;
}
