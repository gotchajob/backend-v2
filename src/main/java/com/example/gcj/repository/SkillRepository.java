package com.example.gcj.repository;

import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByCategoryId(long categoryId);

}
