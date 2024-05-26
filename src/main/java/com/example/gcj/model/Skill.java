package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Skill extends AbstractEntity {
    private long categoryId;
    private String name;
}
