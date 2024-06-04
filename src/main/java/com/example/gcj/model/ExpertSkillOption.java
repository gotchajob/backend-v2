package com.example.gcj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertSkillOption extends AbstractEntity {
    private long expertId;
    private int defaultPoint;
    private String certification;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_option_id", referencedColumnName = "id")
    private SkillOption skillOption;
}
