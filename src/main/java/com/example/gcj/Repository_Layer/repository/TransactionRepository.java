package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(long id);

    Page<Transaction> findByAccountIdAndStatus(long id, int status, Pageable pageable);

    List<Transaction> findByStatusAndTransactionTypeIdAndCreatedAtBefore(int status,int transactionTypeId, LocalDateTime date);

    Transaction findByReferIdAndTransactionTypeId(long referId, long transactionTypeId);
}
