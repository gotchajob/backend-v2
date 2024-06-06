package com.example.gcj.repository;

import com.example.gcj.model.ExpertRegisterUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRegisterUpdateRepository extends JpaRepository<ExpertRegisterUpdate, Long> {
    @Query(value = "SELECT * FROM expert_register_update WHERE request_id = :requestId", nativeQuery = true)
    ExpertRegisterUpdate findByRequestId(long requestId);
}
