package com.example.gcj.Service_Layer.dto.booking_customer_feedback_question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingCustomerFeedbackQuestionRequestDTO {
    private String question;
    private String type;
}
