package com.example.gcj.service;

import com.example.gcj.dto.expert_question_category.CreateExpertQuestionCategoryRequestDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryResponseDTO;

import java.util.List;

public interface ExpertQuestionCategoryService {
    boolean delete(long id);

    boolean delete(long id, long expertId);

    boolean create(long expertId, CreateExpertQuestionCategoryRequestDTO request);

    ExpertQuestionCategoryResponseDTO getById(long id);

    List<ExpertQuestionCategoryListResponseDTO> getByExpertId(long expertId);

    List<ExpertQuestionCategoryListResponseDTO> get();
}
