package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Service_Layer.dto.skill.UpdateSkillRequestDTO;

import java.util.List;

public interface SkillService {
    List<SkillResponseDTO> getAll(Long categoryId);
    List<SkillResponseDTO> findSkillByCategoryId(long categoryId);
    void createSkill(CreateSkillRequestDTO request);
    void deleteSkill(long id);
    boolean deleteSkillByCategoryId(long categoryId);

    boolean update(long id, UpdateSkillRequestDTO request);
}
