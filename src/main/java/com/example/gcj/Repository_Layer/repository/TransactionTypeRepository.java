package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    TransactionType findById(long id);
}
