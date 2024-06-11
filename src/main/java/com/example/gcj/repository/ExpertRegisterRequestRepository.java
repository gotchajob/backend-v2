package com.example.gcj.repository;

import com.example.gcj.model.ExpertRegisterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRegisterRequestRepository extends JpaRepository<ExpertRegisterRequest, Long> {
    ExpertRegisterRequest getById(long id);
    ExpertRegisterRequest getByEmailAndStatus(String email, int status);
    ExpertRegisterRequest getByEmail(String email);
}
