package com.example.gcj.Service_Layer.dto.booking_customer_feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookingCustomerFeedbackTotalRatingResponseDTO {
    private int rating;
    private long count;
}
