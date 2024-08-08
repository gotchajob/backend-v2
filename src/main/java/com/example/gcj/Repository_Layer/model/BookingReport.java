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
    private String content;
    private int status;
    private long bookingId;
}
