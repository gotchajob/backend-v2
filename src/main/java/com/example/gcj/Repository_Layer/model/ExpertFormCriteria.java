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
public class ExpertFormCriteria extends AbstractEntity {
    private Long expertRequestId;
    private String criteria;
    private String description;
    private int status;
}
