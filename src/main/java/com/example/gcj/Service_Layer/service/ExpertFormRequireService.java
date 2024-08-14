package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.UpdateExpertFormRequireRequestDTO;

import java.util.List;

public interface ExpertFormRequireService {
    boolean create(CreateExpertFormRequireRequestDTO request);
    List<ExpertFormRequireResponseDTO> getList(List<Long> categoryIds);
    List<ExpertFormRequireResponseDTO> getList(Long categoryId);

    boolean update(long id, UpdateExpertFormRequireRequestDTO request);

    boolean delete(long id);
}
