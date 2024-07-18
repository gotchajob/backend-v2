package com.example.gcj.dto.booking_customer_feedback_question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingCustomerFeedbackQuestionRequestDTO {
    private String question;
    private String type;
}
