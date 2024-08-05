package com.example.gcj.Service_Layer.dto.transaction;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class TransactionCurrentListResponseDTO {
    private long id;
    private double amount;
    private double balanceAfterTransaction;
    private long typeId;
    private String description;
    private Date createdAt;
}
