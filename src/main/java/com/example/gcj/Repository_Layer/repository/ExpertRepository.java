package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Expert;
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
}
