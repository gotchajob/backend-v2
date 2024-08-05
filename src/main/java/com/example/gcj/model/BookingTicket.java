package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingTicket extends AbstractEntity{
    private long customerId;
    private LocalDateTime expireDate;
    private LocalDateTime dateUsed;
    private Long bookingId;
    private int status;
}
