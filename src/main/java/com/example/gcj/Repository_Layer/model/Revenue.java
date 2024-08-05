package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long transactionId;
    private double amount;
    private int percent;
    @CreationTimestamp
    private Date createdAt;


}
