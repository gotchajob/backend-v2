package com.example.gcj.repository;

import com.example.gcj.model.ExpertNationSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertNationSupportRepository extends JpaRepository<ExpertNationSupport, Long> {
    ExpertNationSupport findByNationAndExpertId(String nation, long expertId);
    List<ExpertNationSupport> findByExpertId(long expertId);
    List<ExpertNationSupport> findAllByNationIn(List<String> nations);
}
