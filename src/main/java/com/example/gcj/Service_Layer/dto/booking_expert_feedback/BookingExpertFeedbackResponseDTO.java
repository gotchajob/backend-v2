package com.example.gcj.Service_Layer.dto.booking_expert_feedback;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class BookingExpertFeedbackResponseDTO {
    private long id;
    private long bookingId;
    private String comment;
    private Date createdAt;

    private List<BookingExpertFeedbackAnswerListResponseDTO> answer;
}
