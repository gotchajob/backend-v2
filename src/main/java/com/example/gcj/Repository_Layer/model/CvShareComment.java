package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CvShareComment extends AbstractEntity {
    private long cvShareId;
    private long customerId;
    private String comment;
    private int status;
}
