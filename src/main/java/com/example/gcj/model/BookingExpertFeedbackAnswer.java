package com.example.gcj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingExpertFeedbackAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "booking_expert_feedback_id")
    private long feedbackId;
    @ManyToOne(fetch = FetchType.LAZY)
    private BookingExpertFeedbackQuestion question;
    private String answer;

}
