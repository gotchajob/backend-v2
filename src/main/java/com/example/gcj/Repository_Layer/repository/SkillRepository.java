package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByCategoryId(long categoryId);

}
