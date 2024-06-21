package com.example.gcj.service.impl;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Transaction;
import com.example.gcj.repository.AccountRepository;
import com.example.gcj.repository.TransactionRepository;
import com.example.gcj.service.TransactionService;
import com.example.gcj.util.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public PageResponseDTO<TransactionResponseDTO> getByUserId(long userId, int pageNumber, int pageSize) {
        Long accountId = accountRepository.getAccountIdByUserId(userId);
        if (accountId == null) {
            throw new CustomException("not found account with user id" + userId);
        }

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Transaction> transactionPage = transactionRepository.findByAccountId(accountId, pageable);
        return new PageResponseDTO<>(transactionPage.map(TransactionMapper::toDto).toList(), transactionPage.getTotalPages());
    }
}
