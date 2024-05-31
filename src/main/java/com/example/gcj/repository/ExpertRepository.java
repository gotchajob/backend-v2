package com.example.gcj.repository;

import com.example.gcj.model.Expert;
import com.example.gcj.model.ExpertSkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    List<Expert> findAllByYearExperienceAfter(int yearExperience);
    Expert getById(long id);
}
