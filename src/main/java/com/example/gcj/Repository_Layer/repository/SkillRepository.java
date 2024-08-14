package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s WHERE s.categoryId =:categoryId AND s.status = 1")
    List<Skill> findAllByCategoryId(long categoryId);

    @Query("SELECT s FROM Skill s WHERE s.id =:id AND s.status = 1")
    Skill findById(long id);
    @Query("SELECT s FROM Skill s WHERE (:categoryId is null OR s.categoryId =:categoryId) AND s.status = 1")
    List<Skill> findAll(Long categoryId);

}
