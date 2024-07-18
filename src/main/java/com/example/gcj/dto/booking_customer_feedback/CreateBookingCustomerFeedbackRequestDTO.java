package com.example.gcj.dto.booking_customer_feedback;

import com.example.gcj.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingCustomerFeedbackRequestDTO {
    private long bookingId;
    private int rating;
    private String comment;

    List<CreateBookingCustomerFeedbackAnswerRequestDTO> answers;
}
