package com.example.gcj.service.impl;

import com.example.gcj.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.model.TransactionType;
import com.example.gcj.repository.TransactionTypeRepository;
import com.example.gcj.service.TransactionTypeService;
import com.example.gcj.util.mapper.TransactionTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final  TransactionTypeRepository transactionTypeRepository;

    @Override
    public List<TransactionTypeListResponseDTO> getAll() {
        List<TransactionType> typeList = transactionTypeRepository.findAll();
        return typeList.stream().map(TransactionTypeMapper::toDto).toList();
    }
}
