package com.example.gcj.service;

import com.example.gcj.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.dto.expert_form_require.ExpertFormRequireResponseDTO;

import java.util.List;

public interface ExpertFormRequireService {
    boolean create(CreateExpertFormRequireRequestDTO request);
    List<ExpertFormRequireResponseDTO> getList(List<Long> categoryIds);
    List<ExpertFormRequireResponseDTO> getList(Long categoryId);
}
