package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertRegisterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRegisterRequestRepository extends JpaRepository<ExpertRegisterRequest, Long> {
    ExpertRegisterRequest getById(long id);
    ExpertRegisterRequest getByEmailAndStatus(String email, int status);
    ExpertRegisterRequest getByEmail(String email);
}
