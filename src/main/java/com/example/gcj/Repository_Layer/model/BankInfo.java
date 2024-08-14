package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankInfo extends AbstractEntity {
    private String bankCode;
    private String numberCard;
    private String nameHolder;
    private int status;
    private long accountId;
}
