package com.example.gcj.dto.transaction;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class TransactionResponseDTO implements Serializable {
    private long id;
    private double amount;
    private long typeId;
    private String description;
    private Date createdAt;
}
