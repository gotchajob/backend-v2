package com.example.gcj.repository;

import com.example.gcj.model.ExpertSkillOption;
import com.example.gcj.model.SkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertSkillOptionRepository extends JpaRepository<ExpertSkillOption, Long> {
    @Query("SELECT e FROM ExpertSkillOption e WHERE e.expertId = :expertId AND e.status = 1")
    List<ExpertSkillOption> findByExpertId(@Param("expertId") long expertId);

    ExpertSkillOption findById(long id);

    List<ExpertSkillOption> findAllBySkillOptionIdInAndStatus(List<Long> skillOptionIds, int status);
}
