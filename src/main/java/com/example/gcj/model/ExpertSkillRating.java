package com.example.gcj.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Long expertFeedBackId;
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_skill_option_id", referencedColumnName = "id")
    private ExpertSkillOption expertSkillOption;
}
