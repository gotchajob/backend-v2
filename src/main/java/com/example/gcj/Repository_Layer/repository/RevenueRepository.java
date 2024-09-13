package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Revenue findById(long id);

    @Query("SELECT SUM(r.amount) FROM Revenue r")
    Double totalRevenue();

    @Query("SELECT MONTH(r.createdAt) AS month, SUM(r.amount) AS totalAmount " +
            "FROM Revenue r " +
            "WHERE YEAR(r.createdAt) = :year " +
            "GROUP BY MONTH(r.createdAt) " +
            "ORDER BY MONTH(r.createdAt)")
    List<Object[]> getTotalAmountByMonth(@Param("year") int year);
}
