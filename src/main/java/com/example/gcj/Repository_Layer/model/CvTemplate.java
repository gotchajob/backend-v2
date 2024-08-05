package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CvTemplate extends AbstractEntity {
    private long categoryId;
    private String name;
    private String templateJson;
    private String image;
    private int status;
    private long createdBy;
}
