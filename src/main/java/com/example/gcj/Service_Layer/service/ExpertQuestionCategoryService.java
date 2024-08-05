package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_question_category.CreateExpertQuestionCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.ExpertQuestionCategoryResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.UpdateExpertQuestionCategoryRequestDTO;

import java.util.List;

public interface ExpertQuestionCategoryService {
    boolean delete(long id);

    boolean delete(long id, long expertId);

    boolean create(long expertId, CreateExpertQuestionCategoryRequestDTO request);

    ExpertQuestionCategoryResponseDTO getById(long id);

    List<ExpertQuestionCategoryListResponseDTO> getByExpertId(long expertId);

    List<ExpertQuestionCategoryListResponseDTO> get();

    boolean update(long id, UpdateExpertQuestionCategoryRequestDTO request, long expertId);
}
