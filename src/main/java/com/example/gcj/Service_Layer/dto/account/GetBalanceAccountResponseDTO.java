package com.example.gcj.Service_Layer.dto.account;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class GetBalanceAccountResponseDTO implements Serializable {
    private double balance;
}
