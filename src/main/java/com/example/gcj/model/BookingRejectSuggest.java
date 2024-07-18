package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingRejectSuggest extends AbstractEntity {
    private String content;
    private String description;
    private int type;
}
