package com.example.gcj.dto.booking_customer_feedback;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookingCustomerFeedbackResponseDTO {
    private long id;
    private long bookingId;
    private long rating;
    private String comment;

    private List<String> answerList;
}
