package com.example.gcj.Service_Layer.dto.booking_customer_feedback_question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookingCustomerFeedbackQuestionResponseDTO {
    private long id;
    private String question;
    private String type;
}
