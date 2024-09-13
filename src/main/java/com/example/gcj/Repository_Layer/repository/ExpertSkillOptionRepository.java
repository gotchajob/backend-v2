package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.ExpertSkillOption;
import com.example.gcj.Service_Layer.dto.dash_board.ExpertDashboardResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionBookingResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertSkillOptionRepository extends JpaRepository<ExpertSkillOption, Long> {
    @Query("SELECT e FROM ExpertSkillOption e WHERE e.expertId = :expertId AND e.status != 0")
    List<ExpertSkillOption> findByExpertId(@Param("expertId") long expertId);
    List<ExpertSkillOption> findByExpertIdAndStatus(long expertId, int status);

    ExpertSkillOption findById(long id);
    ExpertSkillOption findByExpertIdAndSkillOptionId(long expertId, long skillOptionId);

    List<ExpertSkillOption> findAllBySkillOptionIdInAndStatus(List<Long> skillOptionIds, int status);

    @Query("SELECT eso, SUM(esr.rating) as sumPoints, COUNT(esr) as ratingCount " +
            "FROM ExpertSkillOption eso " +
            "LEFT JOIN eso.expertSkillRatings esr " +
            "WHERE eso.skillOption.id = :skillOptionId " +
            "GROUP BY eso")
    List<Object[]> findExpertSkillOptionsWithRatingStatsBySkillOptionId(@Param("skillOptionId") Long skillOptionId);

    @Query("SELECT eso, SUM(esr.rating) as sumPoints, COUNT(esr) as ratingCount " +
            "FROM ExpertSkillOption eso " +
            "LEFT JOIN eso.expertSkillRatings esr " +
            "WHERE eso.expertId = :expertId and eso.status = 1" +
            "GROUP BY eso")
    List<Object[]> findWithRating(@Param("expertId") Long expertId);

    @Query("SELECT eso, SUM(esr.rating) as sumPoints, COUNT(esr) as ratingCount " +
            "FROM ExpertSkillOption eso " +
            "LEFT JOIN eso.expertSkillRatings esr " +
            "WHERE eso.skillOption.id = :skillOptionId AND (eso.expertId in :expertIds)" +
            "GROUP BY eso")
    List<Object[]> findExpertSkillOptionsWithRatingStatsBySkillOptionIdAndExpertIdIn(@Param("skillOptionId") Long skillOptionId, @Param("expertIds") List<Long> expertIds);

    @Modifying
    @Transactional
    @Query("UPDATE ExpertSkillOption e SET e.status = 0 WHERE e.expertId = :expertId")
    int updateStatusByExpertId(@Param("expertId") long expertId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.skill_option.SkillOptionBookingResponseDTO(eso.id, s.id, s.name, so.id, so.name) " +
            "FROM ExpertSkillOption eso " +
            "JOIN SkillOption so ON eso.skillOption.id = so.id " +
            "JOIN Skill s ON so.skillId = s.id " +
            "WHERE eso.id IN (:expertSkillOptionIds) " +
            "GROUP BY eso.id, s.id, s.name, so.id, so.name")
    List<SkillOptionBookingResponseDTO> getByExpertSkillOptionId(List<Long> expertSkillOptionIds);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.dash_board.ExpertDashboardResponseDTO(c.name, COALESCE(count(distinct eso.expertId), 0)) " +
            "FROM Category c " +
            "LEFT JOIN Skill s ON s.categoryId = c.id " +
            "LEFT JOIN SkillOption so ON so.skillId = s.id " +
            "LEFT JOIN ExpertSkillOption eso ON eso.skillOption.id = so.id " +
            "LEFT JOIN Expert e ON e.id = eso.expertId " +
            "WHERE (eso.status != 0 OR eso IS NULL) AND c.status = 1 AND (s.status = 1 OR s.status IS NULL) AND (so.status = 1 OR so.status IS NULL) AND (e.status = 1 OR e IS NULL) " +
            "GROUP BY c.id, c.name")
    List<ExpertDashboardResponseDTO> countExpertByCategory();
}
