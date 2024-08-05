package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertQuestionCategory extends AbstractEntity{
    private String category;
    private String description;
    private Long createdBy;

    public ExpertQuestionCategory(long id) {
        this.setId(id);
    }
}
