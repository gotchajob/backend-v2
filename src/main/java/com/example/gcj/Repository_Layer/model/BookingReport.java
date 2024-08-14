package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingReport extends AbstractEntity {
    private String customerContent;
    private String customerEvidence;
    private String expertContent;
    private String expertEvidence;
    private String staffNote;
    private Long processingBy;
    private int status;
    private long bookingId;
}
