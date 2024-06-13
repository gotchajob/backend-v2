package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends AbstractEntity {
    private long accountId;
    private double amount;
    private String type;
    private String description;
}
