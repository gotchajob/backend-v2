package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.SkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillOptionRepository extends JpaRepository<SkillOption, Long> {

    @Query("SELECT so FROM SkillOption so WHERE so.status = 1")
    List<SkillOption> findAll();

    @Query("SELECT so FROM SkillOption so WHERE so.skillId =:skillId AND so.status = 1")
    List<SkillOption> findBySkillId(long skillId);
    @Query("SELECT so FROM SkillOption so WHERE so.id =:id AND so.status = 1")
    SkillOption findById(long id);

    @Query("SELECT so " +
            "FROM SkillOption so " +
            "JOIN Skill s on so.skillId = s.id " +
            "JOIN Category c on s.categoryId = c.id " +
            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:skillId IS NULL OR s.id = :skillId) " +
            "AND so.status = 1 AND c.status = 1 AND s.status = 1")
    List<SkillOption> findByCategoryAndSkillId(Long categoryId, Long skillId);
}
