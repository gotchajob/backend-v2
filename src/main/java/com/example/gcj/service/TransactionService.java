package com.example.gcj.service;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;

public interface TransactionService {
    PageResponseDTO<TransactionResponseDTO> getByUserId(long userId, int pageNumber, int pageSize);
    boolean create(long amount, String description, int type, Integer relationId);
}
