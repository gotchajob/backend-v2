package com.example.gcj.util.mapper;

import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.model.Transaction;

public class TransactionMapper {
    public static TransactionResponseDTO toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionResponseDTO
                .builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .typeId(transaction.getTransactionTypeId())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
