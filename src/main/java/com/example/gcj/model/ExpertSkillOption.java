package com.example.gcj.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "expertSkillOption", fetch = FetchType.LAZY)
    private List<ExpertSkillRating> expertSkillRatings;

    public ExpertSkillOption(long id) {
        this.setId(id);
    }
}
