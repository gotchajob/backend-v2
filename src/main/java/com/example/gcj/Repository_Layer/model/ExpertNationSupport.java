package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertNationSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long expertId;
    private String nation;
    private int status;
}
