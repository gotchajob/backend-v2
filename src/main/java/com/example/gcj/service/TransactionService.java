package com.example.gcj.service;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionCurrentListResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;

public interface TransactionService {
    PageResponseDTO<TransactionCurrentListResponseDTO> getByAccountId(long accountId, int pageNumber, int pageSize);
    PageResponseDTO<TransactionResponseDTO> getAll(int pageNumber, int pageSize, String sortBy, String... search);
    boolean create(long amount, String description, int type, Integer relationId);

    long transactionForDeposit(long amount, String description);
    Transaction getById(long id);

    Transaction updateTransactionStatus(long id, int status, Long referId);

    @Scheduled(fixedRate = 60000)
    void autoFailTransaction();
}
