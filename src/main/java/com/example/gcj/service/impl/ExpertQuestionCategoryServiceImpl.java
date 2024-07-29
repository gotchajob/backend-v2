package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_question_category.CreateExpertQuestionCategoryRequestDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertQuestionCategory;
import com.example.gcj.repository.ExpertQuestionCategoryRepository;
import com.example.gcj.service.ExpertQuestionCategoryService;
import com.example.gcj.util.mapper.ExpertQuestionCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertQuestionCategoryServiceImpl implements ExpertQuestionCategoryService {
    private final ExpertQuestionCategoryRepository expertQuestionCategoryRepository;

    @Override
    public boolean delete(long id) {
        if (!expertQuestionCategoryRepository.existsById(id)) {
            throw new CustomException("not found expert question category with id " + id);
        }

        expertQuestionCategoryRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean delete(long id, long expertId) {
        ExpertQuestionCategory category = expertQuestionCategoryRepository.findById(id);
        if (category == null) {
            throw new CustomException("not found expert question category with id " + id);
        }
       if (category.getCreatedBy() != expertId) {
           throw new CustomException("current expert not same with expert in category");
       }

        expertQuestionCategoryRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean create(long expertId, CreateExpertQuestionCategoryRequestDTO request) {
        //todo: check request data

        ExpertQuestionCategory build = ExpertQuestionCategory
                .builder()
                .category(request.getCategory())
                .description(request.getDescription())
                .createdBy(expertId)
                .build();
        expertQuestionCategoryRepository.save(build);
        return true;
    }

    @Override
    public ExpertQuestionCategoryResponseDTO getById(long id) {
        ExpertQuestionCategory category = expertQuestionCategoryRepository.findById(id);
        if (category == null) {
            throw new CustomException("not found expert question category with id " + id);
        }

        return ExpertQuestionCategoryResponseDTO
                .builder()
                .id(category.getId())
                .category(category.getCategory())
                .description(category.getDescription())
                .build();
    }

    @Override
    public List<ExpertQuestionCategoryListResponseDTO> getByExpertId(long expertId) {
        List<ExpertQuestionCategory> list = expertQuestionCategoryRepository.findByCreatedBy(expertId);
        return list.stream().map(ExpertQuestionCategoryMapper::toDto).toList();
    }

    @Override
    public List<ExpertQuestionCategoryListResponseDTO> get() {
        List<ExpertQuestionCategory> list = expertQuestionCategoryRepository.findAll();
        return list.stream().map(ExpertQuestionCategoryMapper::toDto).toList();
    }
}
