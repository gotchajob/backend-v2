package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    boolean existsByUserId(long userId);

    @Query("SELECT s.id FROM Staff s INNER JOIN User u ON s.userId = u.id WHERE u.email =:email ")
    Long currentStaffId(String email);
}
