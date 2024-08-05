package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingExpertFeedbackQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String question;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    private ExpertQuestionCategory category;
    private long createdBy;
}
