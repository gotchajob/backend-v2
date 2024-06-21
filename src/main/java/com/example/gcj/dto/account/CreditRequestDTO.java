package com.example.gcj.dto.account;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreditRequestDTO implements Serializable {
    private double amount;
    private String description;
}
