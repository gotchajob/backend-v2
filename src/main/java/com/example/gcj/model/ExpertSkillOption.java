package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertSkillOption extends AbstractEntity {
    private long expertId;
    private long skillOptionId;
    private long defaultPoint;
    private String certificate;
    private int status;
}