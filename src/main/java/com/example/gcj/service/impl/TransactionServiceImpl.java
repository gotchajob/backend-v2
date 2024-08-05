package com.example.gcj.service.impl;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionCurrentListResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Transaction;
import com.example.gcj.repository.SearchRepository;
import com.example.gcj.repository.TransactionRepository;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.service.TransactionService;
import com.example.gcj.util.mapper.TransactionMapper;
import com.example.gcj.util.status.TransactionStatus;
import com.example.gcj.util.status.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final PolicyService policyService;
    private final SearchRepository searchRepository;

    @Override
    public PageResponseDTO<TransactionCurrentListResponseDTO> getByAccountId(long accountId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Transaction> transactionPage = transactionRepository.findByAccountIdAndStatus(accountId, TransactionStatus.COMPLETE, pageable);

        return new PageResponseDTO<>(transactionPage.map(TransactionMapper::toDtoCurrent).toList(), transactionPage.getTotalPages());
    }

    @Override
    public PageResponseDTO<TransactionResponseDTO> getAll(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<Transaction> entitiesPage = searchRepository.getEntitiesPage(Transaction.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(entitiesPage.map(TransactionMapper::toDto).toList(), entitiesPage.getTotalPages());
    }

    @Override
    public boolean create(long amount, String description, int type, Integer relationId) {

        return false;
    }

    @Override
    public long transactionForDeposit(long amount, String description) {
        Transaction transaction = Transaction
                .builder()
                .transactionTypeId(TransactionType.DEPOSIT)
                .amount(amount)
                .description(description)
                .status(2)
                .referId(null)
                .accountId(accountService.getCurrentAccountId())
                .build();
        Transaction save = transactionRepository.save(transaction);
        return save.getId();
    }

    @Override
    public Transaction getById(long id) {
        Transaction transaction = transactionRepository.findById(id);
        if (transaction == null) {
            throw new CustomException("not found transaction with id " + id);
        }

        return transaction;
    }

    @Override
    public Transaction updateTransactionStatus(long id, int status) {
        Transaction transaction = getById(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void autoFailTransaction() {
        long minusToAutoCancel = policyService.getByKey(PolicyKey.MINUS_TO_AUTO_CANCEL_TRANSACTION, Long.class);
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minusToAutoCancel);

        List<Transaction> transactionList = transactionRepository.findByStatusAndCreatedAtBefore(TransactionStatus.PROCESSING, localDateTime);
        for (Transaction transaction : transactionList) {
            transaction.setStatus(TransactionStatus.FAIL);
        }
        transactionRepository.saveAll(transactionList);
    }
}
