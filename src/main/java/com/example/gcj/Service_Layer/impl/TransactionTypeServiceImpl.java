package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.TransactionType;
import com.example.gcj.Repository_Layer.repository.TransactionTypeRepository;
import com.example.gcj.Service_Layer.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.Service_Layer.service.TransactionTypeService;
import com.example.gcj.Shared.util.mapper.TransactionTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Override
    public List<TransactionTypeListResponseDTO> getAll() {
        List<TransactionType> typeList = transactionTypeRepository.findAll();
        return typeList.stream().map(TransactionTypeMapper::toDto).toList();
    }
}
