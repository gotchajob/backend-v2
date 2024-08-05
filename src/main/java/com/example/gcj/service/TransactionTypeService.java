package com.example.gcj.service;

import com.example.gcj.dto.transaction_type.TransactionTypeListResponseDTO;

import java.util.List;

public interface TransactionTypeService {
    List<TransactionTypeListResponseDTO> getAll();
}
