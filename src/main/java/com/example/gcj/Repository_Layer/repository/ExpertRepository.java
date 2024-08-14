package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Expert;
import com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO;
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

    @Query("SELECT u.email FROM Expert e INNER JOIN User u ON e.user.id = u.id  WHERE e.id =:expertId")
    String getEmailById(long expertId);

    @Query("SELECT e.user.id FROM Expert e WHERE e.id =:id")
    Long getUserIdById(long id);

    @Query("SELECT count(e) > 0 FROM Expert e WHERE e.user.id =:userId AND e.status != 0")
    boolean existsByUserIdAndStatusNotZero(long userId);

    @Query("SELECT new com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO( " +
            "u.id, e.id, u.firstName, u.lastName, u.avatar, u.email, e.yearExperience, e.bio, e.cost, " +
            "(CASE WHEN e.yearExperience >= :yearExperience THEN :weightExperience ELSE 0 END + " +
            "COALESCE((SELECT SUM(CASE WHEN ens.nation IN :nations THEN :weightNation ELSE 0 END) " +
            "FROM ExpertNationSupport ens WHERE ens.expertId = e.id), 0) + " +
            "COALESCE((SELECT SUM(CASE WHEN eso.skillOption.id IN :skillOptionIds THEN :weightSkill ELSE 0 END) " +
            "FROM ExpertSkillOption eso WHERE eso.expertId = e.id), 0) + " +
            "(CASE WHEN e.personalPoint >= :personalPoint THEN :weightPersonalPoint ELSE 0 END)) AS point) " +
            "FROM Expert e " +
            "JOIN e.user u " +
            "WHERE u.status = 1 AND e.status = 1 AND (:main IS NULL OR " +
            "(:main = 1 AND e.yearExperience >= :yearExperience) OR " +
            "(:main = 2 AND EXISTS (SELECT 1 FROM ExpertNationSupport ens WHERE ens.expertId = e.id AND ens.nation IN :nations)) OR " +
            "(:main = 3 AND EXISTS (SELECT 1 FROM ExpertSkillOption eso WHERE eso.expertId = e.id AND eso.skillOption.id IN :skillOptionIds))) " +
            "GROUP BY e.id, u.id " +
            "ORDER BY point DESC, e.personalPoint DESC")
    List<ExpertMatchListResponseDTO> findMatchingExperts(
            @Param("yearExperience") Integer yearExperience,
            @Param("nations") List<String> nations,
            @Param("skillOptionIds") List<Long> skillOptionIds,
            @Param("personalPoint") Integer personalPoint,
            @Param("weightExperience") double weightExperience,
            @Param("weightNation") double weightNation,
            @Param("weightSkill") double weightSkill,
            @Param("weightPersonalPoint") double weightPersonalPoint,
            @Param("main") Integer main,
            Pageable pageable
    );

}
