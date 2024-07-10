package com.example.gcj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long expertSkillOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;
}
