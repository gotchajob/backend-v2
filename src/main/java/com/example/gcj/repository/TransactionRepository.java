package com.example.gcj.repository;

import com.example.gcj.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(long id);
    Page<Transaction> findByAccountId(long id, Pageable pageable);
}
