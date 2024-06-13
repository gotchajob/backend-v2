package com.example.gcj.service.impl;

import com.example.gcj.repository.TransactionRepository;
import com.example.gcj.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

}
