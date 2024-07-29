package com.example.gcj.dto.booking_expert_feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookingExpertFeedbackListResponseDTO {
    private long id;
    private String comment;
}
