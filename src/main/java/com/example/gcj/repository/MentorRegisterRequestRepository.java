package com.example.gcj.repository;

import com.example.gcj.model.ExpertRegisterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRegisterRequestRepository extends JpaRepository<ExpertRegisterRequest, Long> {
    List<ExpertRegisterRequest> getAllByStatus(int status, Pageable pageable);
    long countByStatus(int status);
    ExpertRegisterRequest getById(long id);
}
