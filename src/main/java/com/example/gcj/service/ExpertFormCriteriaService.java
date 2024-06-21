package com.example.gcj.service;

import com.example.gcj.dto.expert_form_criteria.CreateExpertFormCriteriaRequestDTO;
import com.example.gcj.dto.expert_form_criteria.ExpertFormCriteriaResponseDTO;
import com.example.gcj.dto.expert_form_require.CreateExpertFormRequireRequestDTO;

import java.util.List;

public interface ExpertFormCriteriaService {
    boolean create(long expertRegisterRequestId, List<CreateExpertFormCriteriaRequestDTO> request);
    List<ExpertFormCriteriaResponseDTO> getList(Long expertRequestId);
}
