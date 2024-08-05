package com.example.gcj.util.mapper;

import com.example.gcj.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.model.TransactionType;

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
