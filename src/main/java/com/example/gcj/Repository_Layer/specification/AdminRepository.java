package com.example.gcj.Repository_Layer.specification;

import com.example.gcj.Repository_Layer.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUserId(long userId);
}
