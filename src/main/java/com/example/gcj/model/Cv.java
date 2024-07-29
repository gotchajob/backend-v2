package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cv extends AbstractEntity {
    private long cvTemplateId;
    private Long customerId;
    private String name;
    private String cv;
    private String image;
    private int status;
}
