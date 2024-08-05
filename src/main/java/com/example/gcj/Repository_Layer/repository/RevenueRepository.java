package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Revenue findById(long id);
}
