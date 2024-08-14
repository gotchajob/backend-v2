package com.example.gcj.Service_Layer.dto.bank_info;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BankInfoResponseDTO {
    private long id;
    private long accountId;
    private String bankCode;
    private String numberCard;
    private String nameHolder;
    private int status;
}
