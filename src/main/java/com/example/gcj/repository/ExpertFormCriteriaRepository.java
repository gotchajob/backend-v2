package com.example.gcj.repository;

import com.example.gcj.model.ExpertFormCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertFormCriteriaRepository extends JpaRepository<ExpertFormCriteria, Long> {
    ExpertFormCriteria findById(long id);
    @Query("SELECT e FROM ExpertFormCriteria e WHERE e.expertRequestId =:expertRequestId AND e.status != 0")
    List<ExpertFormCriteria> findByExpertRequestId(@Param("expertRequestId") long expertRequestId);
    List<ExpertFormCriteria> getByStatusNot(int status);

    @Transactional
    @Modifying
    @Query("UPDATE ExpertFormCriteria e SET e.status = 0 WHERE e.expertRequestId =:expertRequestId")
    int updateByExpertRequestId(@Param("expertRequestId") long expertRequestId);
}
