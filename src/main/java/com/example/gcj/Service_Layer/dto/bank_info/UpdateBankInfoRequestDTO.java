package com.example.gcj.Service_Layer.dto.bank_info;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBankInfoRequestDTO {
    @NotBlank
    private String bankCode;
    @NotBlank
    private String numberCard;
    @NotBlank
    private String nameHolder;
}
