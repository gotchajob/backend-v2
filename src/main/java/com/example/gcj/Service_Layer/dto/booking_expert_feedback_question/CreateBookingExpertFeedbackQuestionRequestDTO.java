package com.example.gcj.Service_Layer.dto.booking_expert_feedback_question;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingExpertFeedbackQuestionRequestDTO {
    @NotBlank
    private String question;
    @NotBlank
    private String type;
    @Min(1)
    private long categoryId;
}
