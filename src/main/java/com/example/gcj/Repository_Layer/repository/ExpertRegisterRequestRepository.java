package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertRegisterRequest;
import com.example.gcj.Shared.util.status.ExpertRegisterRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRegisterRequestRepository extends JpaRepository<ExpertRegisterRequest, Long> {
    ExpertRegisterRequest getById(long id);
    ExpertRegisterRequest getByEmailAndStatus(String email, int status);
    ExpertRegisterRequest getByEmail(String email);


    long countByStatus(int status);
    long count();

    @Query("SELECT count(e.id) FROM ExpertRegisterRequest e WHERE e.status = " + ExpertRegisterRequestStatus.COMPLETE + " AND month(e.updatedAt) =:month AND YEAR(e.updatedAt) =:year ")
    Long countNewExpert(int month, int year);
}
