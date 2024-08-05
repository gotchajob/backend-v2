package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingCustomerFeedback extends AbstractEntity{
    private long bookingId;
    private int rating;
    private String comment;
    private int status;
}
