package com.example.gcj.Service_Layer.dto.booking_customer_feedback;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class BookingCustomerFeedbackResponseDTO {
    private long id;
    private long bookingId;
    private long rating;
    private String comment;
    private Date createdAt;

    private List<BookingCustomerFeedbackAnswerListResponseDTO> answerList;
}
