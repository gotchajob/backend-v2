package com.example.gcj.Service_Layer.dto.booking_expert_feedback_question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookingExpertFeedbackQuestionListResponseDTO {
    private long id;
    private String question;
    private String type;
    private long categoryId;
    private String category;
}
