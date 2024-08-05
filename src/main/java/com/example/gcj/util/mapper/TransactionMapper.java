package com.example.gcj.util.mapper;

import com.example.gcj.dto.transaction.TransactionCurrentListResponseDTO;
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
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(0)
                .typeId(transaction.getTransactionTypeId())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .referId(transaction.getReferId())
                .build();
    }

    public static TransactionCurrentListResponseDTO toDtoCurrent(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionCurrentListResponseDTO
                .builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(0)
                .typeId(transaction.getTransactionTypeId())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
