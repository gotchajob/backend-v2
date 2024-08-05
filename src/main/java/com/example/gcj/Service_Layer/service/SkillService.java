package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Service_Layer.dto.skill.UpdateSkillRequestDTO;

import java.util.List;

public interface SkillService {
    List<SkillResponseDTO> getAll();
    List<SkillResponseDTO> findSkillByCategoryId(long categoryId);
    void createSkill(CreateSkillRequestDTO request);
    List<UpdateSkillRequestDTO> updateSkill(List<UpdateSkillRequestDTO> request);
    void deleteSkill(long id);
}
