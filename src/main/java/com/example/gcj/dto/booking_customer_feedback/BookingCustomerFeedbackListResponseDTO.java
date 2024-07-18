package com.example.gcj.dto.booking_customer_feedback;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingCustomerFeedbackListResponseDTO {
    private long id;
    private long bookingId;
    private long rating;
    private String comment;
}
