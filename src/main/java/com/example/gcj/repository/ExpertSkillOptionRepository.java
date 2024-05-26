package com.example.gcj.repository;

import com.example.gcj.model.ExpertSkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertSkillOptionRepository extends JpaRepository<ExpertSkillOption, Long> {

}
