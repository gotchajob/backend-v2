package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkillOption extends AbstractEntity {
    private long id;
    private long skillId;
    private String name;
    private int status;
}
