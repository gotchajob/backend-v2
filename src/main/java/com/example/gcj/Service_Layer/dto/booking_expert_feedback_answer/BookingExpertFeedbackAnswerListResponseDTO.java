package com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingExpertFeedbackAnswerListResponseDTO {
    private long id;
    private String question;
    private String questionType;
    private long questionId;
    private String answer;
    private long categoryId;
    private String category;
}
