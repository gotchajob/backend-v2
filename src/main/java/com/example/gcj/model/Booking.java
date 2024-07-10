package com.example.gcj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private long availabilityId;
    private LocalTime startInterviewTime;
    private LocalTime endInterviewTime;
    private LocalDate interviewDate;
    private String note;
    private String rejectReason;
    private Long oldBooking;
    private int status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
    private List<BookingSkill> bookingSkills;

    public Booking(long id) {
        this.setId(id);
    }
}
