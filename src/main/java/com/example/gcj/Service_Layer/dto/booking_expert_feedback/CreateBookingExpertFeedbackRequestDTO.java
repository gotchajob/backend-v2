package com.example.gcj.Service_Layer.dto.booking_expert_feedback;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.CreateBookingExpertFeedbackAnswerRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateBookingExpertFeedbackRequestDTO {
    private long bookingId;
    private String comment;

    private List<CreateBookingExpertFeedbackAnswerRequestDTO> answerList;
}
