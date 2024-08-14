package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.BankInfo;
import com.example.gcj.Service_Layer.dto.bank_info.BankInfoListResponseDTO;

public class BankInfoMapper {
    public static BankInfoListResponseDTO toDto(BankInfo bankInfo) {
        if (bankInfo == null) {
            return null;
        }

        return BankInfoListResponseDTO
                .builder()
                .id(bankInfo.getId())
                .bankCode(bankInfo.getBankCode())
                .numberCard(bankInfo.getNumberCard())
                .nameHolder(bankInfo.getNameHolder())
                .status(bankInfo.getStatus())
                .build();
    }
}
