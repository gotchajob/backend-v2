package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Transaction;
import com.example.gcj.Shared.util.status.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(long id);

    Page<Transaction> findByAccountIdAndStatus(long id, int status, Pageable pageable);

    List<Transaction> findByStatusAndTransactionTypeIdAndCreatedAtBefore(int status,int transactionTypeId, LocalDateTime date);

    Transaction findByReferIdAndTransactionTypeId(long referId, long transactionTypeId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 1 AND t.transactionTypeId = " + TransactionType.DEPOSIT)
    Double totalDeposit();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 1 AND t.transactionTypeId = " + TransactionType.WITHDRAW)
    Double totalWithDraw();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 1 AND t.transactionTypeId = " + TransactionType.PAY_FOR_SERVICE)
    Double totalPayForService();


    @Query("SELECT MONTH(t.createdAt) AS month, SUM(t.amount) AS totalAmount " +
            "FROM Transaction t " +
            "WHERE YEAR(t.createdAt) = :year AND t.status = 1 AND t.transactionTypeId = " + TransactionType.DEPOSIT + " " +
            "GROUP BY MONTH(t.createdAt) " +
            "ORDER BY MONTH(t.createdAt)")
    List<Object[]> getTotalDepositAmountByMonth(@Param("year") int year);

}
