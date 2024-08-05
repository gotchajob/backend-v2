package com.example.gcj.Service_Layer.dto.transaction;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class TransactionResponseDTO implements Serializable {
    private long id;
    private long accountId;
    private double amount;
    private double balanceAfterTransaction;
    private long typeId;
    private int status;
    private String description;
    private Date createdAt;
    private Long referId;
}
