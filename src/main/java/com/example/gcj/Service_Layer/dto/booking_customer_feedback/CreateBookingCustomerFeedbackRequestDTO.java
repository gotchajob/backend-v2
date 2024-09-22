package com.example.gcj.Service_Layer.dto.booking_customer_feedback;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingCustomerFeedbackRequestDTO {
    @Min(1)
    private long bookingId;
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;

    List<CreateBookingCustomerFeedbackAnswerRequestDTO> answers;
    List<CreateExpertSkillRatingRequestDTO> skillRatings;
}
