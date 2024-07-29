package com.example.gcj.repository;

import com.example.gcj.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.id FROM Customer c WHERE c.userId = :userId")
    Long getIdByUserId(@Param("userId") long userId);

    Customer findById(long id);
    Customer findByUserId(long userId);
}
