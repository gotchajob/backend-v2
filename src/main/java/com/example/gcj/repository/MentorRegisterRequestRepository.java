package com.example.gcj.repository;

import com.example.gcj.model.MentorRegisterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRegisterRequestRepository extends JpaRepository<MentorRegisterRequest, Long> {
    List<MentorRegisterRequest> getAllByStatus(int status, Pageable pageable);
    long countByStatus(int status);
    MentorRegisterRequest getById(long id);
}
