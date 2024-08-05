package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.SkillOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillOptionRepository extends JpaRepository<SkillOption, Long> {

    List<SkillOption> findBySkillId(long skillId);
}
