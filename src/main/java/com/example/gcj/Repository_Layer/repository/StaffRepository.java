package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    boolean existsByUserId(long userId);
}
