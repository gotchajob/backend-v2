package com.example.gcj.service;

import com.example.gcj.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.model.Skill;

import java.util.List;

public interface SkillService {
    List<SkillResponseDTO> getAll();
    List<SkillResponseDTO> findSkillByCategoryId(long categoryId);
    void createSkill(CreateSkillRequestDTO request);
    List<UpdateSkillRequestDTO> updateSkill(List<UpdateSkillRequestDTO> request);
    void deleteSkill(long id);
}
