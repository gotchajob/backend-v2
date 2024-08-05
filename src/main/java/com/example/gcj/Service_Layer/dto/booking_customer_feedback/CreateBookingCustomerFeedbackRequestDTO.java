package com.example.gcj.Service_Layer.dto.booking_customer_feedback;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingCustomerFeedbackRequestDTO {
    private long bookingId;
    private int rating;
    private String comment;

    List<CreateBookingCustomerFeedbackAnswerRequestDTO> answers;
    List<CreateExpertSkillRatingRequestDTO> skillRatings;
}
