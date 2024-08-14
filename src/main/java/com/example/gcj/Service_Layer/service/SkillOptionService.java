package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.UpdateSkillOptionRequestDTO;

import java.util.List;

public interface SkillOptionService {
    List<SkillOptionResponseDTO> getAll(Long categoryId, Long skillId);
    List<SkillOptionResponseDTO> findSkillOptionBySkillId(long skillId);
    boolean createSkillOption(CreateSkillOptionRequestDTO request);
    void deleteSkillOptions(long id);
    boolean deleteBySkillId(long skillId);

    boolean update(long id, UpdateSkillOptionRequestDTO request);
}
