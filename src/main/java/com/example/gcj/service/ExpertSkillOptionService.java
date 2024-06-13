package com.example.gcj.service;

import com.example.gcj.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;

import java.util.List;

public interface ExpertSkillOptionService {
    boolean createExpertSkillOption(long expertId, List<CreateExpertSkillOptionDTO> request);
    List<ExpertSkillOptionResponseDTO> getByExpertId(long expertId);
    boolean updateDefaultPoint(List<UpdateExpertSkillOptionPointRequestDTO> request);
    boolean deleteAllByExpertId(long expertId);
}
