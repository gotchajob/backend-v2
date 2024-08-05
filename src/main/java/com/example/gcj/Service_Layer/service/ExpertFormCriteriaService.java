package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_form_criteria.CreateExpertFormCriteriaRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_criteria.ExpertFormCriteriaResponseDTO;

import java.util.List;

public interface ExpertFormCriteriaService {
    boolean create(long expertRegisterRequestId, List<CreateExpertFormCriteriaRequestDTO> request);
    List<ExpertFormCriteriaResponseDTO> getList(Long expertRequestId);
}
