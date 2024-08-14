package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CvShare extends AbstractEntity {
    private long cvId;
    private String cvImage;
    private String caption;
    private long categoryId;
    private int status;
}
