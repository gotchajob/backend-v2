package com.example.gcj.dto.booking_expert_feedback_question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingExpertFeedbackQuestionRequestDTO {
    private String question;
    private String type;
    private long categoryId;
}
