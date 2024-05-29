package com.example.gcj.service;

import com.example.gcj.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.model.Skill;

import java.util.List;

public interface SkillService {
    List<SkillResponseDTO> getAll();
    List<SkillResponseDTO> findSkillByCategoryId(long catogoryId);
    void createSkill(CreateSkillRequestDTO request);
    void updateSkill(long id, UpdateSkillRequestDTO dto);
    void deleteSkill(long id);
}
