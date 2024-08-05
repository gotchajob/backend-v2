package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account extends AbstractEntity {
    private double balance;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
