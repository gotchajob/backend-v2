package com.example.gcj.Service_Layer.dto.booking_expert_feedback_question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingExpertFeedbackQuestionRequestDTO {
    private String question;
    private String type;
    private long categoryId;
}
