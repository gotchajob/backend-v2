package com.example.gcj.Service_Layer.dto.booking_customer_feedback_question;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingCustomerFeedbackQuestionRequestDTO {
    @NotBlank
    private String question;
    @NotBlank
    private String type;
}
