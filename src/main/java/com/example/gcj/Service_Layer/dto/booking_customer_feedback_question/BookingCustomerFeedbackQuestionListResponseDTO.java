package com.example.gcj.Service_Layer.dto.booking_customer_feedback_question;

import lombok.*;

@Getter
@Builder
public class BookingCustomerFeedbackQuestionListResponseDTO {
    private long id;
    private String question;
    private String type;
}
