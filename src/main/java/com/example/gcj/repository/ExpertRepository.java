package com.example.gcj.repository;

import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertSkillOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    List<Expert> findByYearExperienceGreaterThanEqualAndStatus(int yearExperience, int status);
    List<Expert> findByYearExperienceGreaterThanEqualAndStatusAndIdIn(int yearExperience, int status, List<Long> expertIds);
    Expert getById(long id);

    @Query("SELECT e.id FROM Expert e WHERE e.user.id =:userId")
    Long getIdByUserId(@Param("userId") long userId);
}
