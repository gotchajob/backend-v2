package com.example.gcj.Service_Layer.dto.booking_customer_feedback;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class BookingCustomerFeedbackSimpleResponseDTO {
    private long id;
    private long bookingId;
    private long rating;
    private String comment;
    private Date createdAt;
}
