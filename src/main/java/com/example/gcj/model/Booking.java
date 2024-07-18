package com.example.gcj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Booking extends AbstractEntity {
    private long expertId;
    private long customerId;
    private Long customerCvId;
    private String note;
    private String rejectReason;
    private Long oldBooking;
    private int status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
    private List<BookingSkill> bookingSkills;

    @ManyToOne(fetch = FetchType.LAZY)
    private Availability availability;

    public Booking(long id) {
        this.setId(id);
    }
}
