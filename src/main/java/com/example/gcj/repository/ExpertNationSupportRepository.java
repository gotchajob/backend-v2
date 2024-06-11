package com.example.gcj.repository;

import com.example.gcj.model.ExpertNationSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertNationSupportRepository extends JpaRepository<ExpertNationSupport, Long> {
    ExpertNationSupport findByNation(String nation);
    List<ExpertNationSupport> findByExpertId(long expertId);
    List<ExpertNationSupport> findAllByNationIn(List<String> nations);

    @Modifying
    @Transactional
    @Query("UPDATE ExpertNationSupport e SET e.status = 0 WHERE e.expertId = :expertId")
    int updateStatusByExpertId(@Param("expertId") long expertId);
}
