package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.Repository_Layer.model.TransactionType;

public class TransactionTypeMapper {
    public static TransactionTypeListResponseDTO toDto(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }

        return TransactionTypeListResponseDTO
                .builder()
                .id(transactionType.getId())
                .type(transactionType.getType())
                .description(transactionType.getDescription())
                .build();
    }
}
