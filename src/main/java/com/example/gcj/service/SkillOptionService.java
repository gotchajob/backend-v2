package com.example.gcj.service;

import com.example.gcj.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.dto.skill_option.UpdateSkillOptionRequestDTO;

import java.util.List;

public interface SkillOptionService {
    List<SkillOptionResponseDTO> getAll();
    List<SkillOptionResponseDTO> findSkillOptionBySkillId(long skillId);
    boolean createSkillOption(CreateSkillOptionRequestDTO request);
    List<UpdateSkillOptionRequestDTO> updateSkillOptions(List<UpdateSkillOptionRequestDTO> request);
    void deleteSkillOptions(long skillId);
}
