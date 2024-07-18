package com.example.gcj.dto.booking_expert_feedback_question;

import lombok.*;

@Getter
@Builder
public class BookingExpertFeedbackQuestionResponseDTO {
    private long id;
    private String question;
    private String type;
}
