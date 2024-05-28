package com.example.gcj.service;

import com.example.gcj.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.model.SkillOption;

import java.util.List;

public interface SkillOptionService {
    List<SkillOptionResponseDTO> getAll();
    boolean createSkillOption(CreateSkillOptionRequestDTO request);
    List<UpdateSkillOptionRequestDTO> updateSkillOptions(List<UpdateSkillOptionRequestDTO> request);
    boolean deleteSkillOptions(List<Long> skillIds);
}
