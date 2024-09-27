package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertNationSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertNationSupportRepository extends JpaRepository<ExpertNationSupport, Long> {
    ExpertNationSupport findByNationAndExpertId(String nation, long expertId);
    ExpertNationSupport findByNationAndExpertIdAndStatus(String nation, long expertId, int status);
    @Query("SELECT ens FROM ExpertNationSupport ens WHERE ens.status != 0 AND ens.expertId =:expertId ")
    List<ExpertNationSupport> findByExpertId(long expertId);
    @Query("SELECT ens FROM ExpertNationSupport ens WHERE ens.status != 0 AND ens.nation IN (:nations) ")
    List<ExpertNationSupport> findAllByNationIn(List<String> nations);
    List<ExpertNationSupport> findAllByNationInAndExpertIdInAndStatusNot(List<String> nations, List<Long> expertId, int status);

    @Modifying
    @Transactional
    @Query("UPDATE ExpertNationSupport e SET e.status = 0 WHERE e.expertId = :expertId")
    int updateStatusByExpertId(@Param("expertId") long expertId);

    @Query("SELECT ens FROM ExpertNationSupport ens WHERE ens.status != 0 AND ens.id =:id ")
    ExpertNationSupport findById(long id);
    @Query("SELECT ens FROM ExpertNationSupport ens WHERE ens.status != 0 ")
    List<ExpertNationSupport> findAll();
}
