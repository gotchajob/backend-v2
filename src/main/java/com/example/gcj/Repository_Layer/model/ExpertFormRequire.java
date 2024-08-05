package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ExpertFormRequire extends AbstractEntity {
    private Long categoryId;
    private String name;
    private String description;
    private int status;

}
