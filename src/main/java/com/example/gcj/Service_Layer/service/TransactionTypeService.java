package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.transaction_type.TransactionTypeListResponseDTO;

import java.util.List;

public interface TransactionTypeService {
    List<TransactionTypeListResponseDTO> getAll();
}
