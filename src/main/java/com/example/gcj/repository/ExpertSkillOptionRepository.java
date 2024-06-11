package com.example.gcj.repository;

import com.example.gcj.model.ExpertSkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertSkillOptionRepository extends JpaRepository<ExpertSkillOption, Long> {
    @Query("SELECT e FROM ExpertSkillOption e WHERE e.expertId = :expertId AND e.status = 1")
    List<ExpertSkillOption> findByExpertId(@Param("expertId") long expertId);

    ExpertSkillOption findById(long id);
    ExpertSkillOption findByExpertIdAndSkillOptionId(long expertId, long skillOptionId);

    List<ExpertSkillOption> findAllBySkillOptionIdInAndStatus(List<Long> skillOptionIds, int status);
    @Query("SELECT eso, SUM(esr.point) as sumPoints, COUNT(esr) as ratingCount " +
            "FROM ExpertSkillOption eso " +
            "LEFT JOIN eso.expertSkillRatings esr " +
            "WHERE eso.skillOption.id = :skillOptionId " +
            "GROUP BY eso")
    List<Object[]> findExpertSkillOptionsWithRatingStatsBySkillOptionId(@Param("skillOptionId") Long skillOptionId);

    @Modifying
    @Transactional
    @Query("UPDATE ExpertSkillOption e SET e.status = 0 WHERE e.expertId = :expertId")
    int updateStatusByExpertId(@Param("expertId") long expertId);
}
