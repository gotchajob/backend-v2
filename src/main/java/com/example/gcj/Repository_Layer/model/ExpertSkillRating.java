package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertSkillRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long bookingCustomerFeedbackId;
    private int rating;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_skill_option_id", referencedColumnName = "id")
    private ExpertSkillOption expertSkillOption;

    @CreationTimestamp
    private Date createdAt;
}
