package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ExpertFormRequire;
import com.example.gcj.Repository_Layer.repository.CategoryRepository;
import com.example.gcj.Repository_Layer.repository.ExpertFormRequireRepository;
import com.example.gcj.Service_Layer.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.Service_Layer.service.ExpertFormRequireService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.ExpertFormRequireMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertFormRequireServiceImpl implements ExpertFormRequireService {
    private final ExpertFormRequireRepository expertFormRequireRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public boolean create(CreateExpertFormRequireRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        if (request.getCategoryId() != null && !categoryRepository.existsById(request.getCategoryId())) {
            throw new CustomException("not found category with id " + request.getCategoryId());
        }

        ExpertFormRequire expertFormRequire = ExpertFormRequire
                .builder()
                .categoryId(request.getCategoryId())
                .name(request.getName())
                .description(request.getDescription())
                .status(Status.ACTIVE)
                .build();
        expertFormRequireRepository.save(expertFormRequire);

        return true;
    }

    @Override
    public List<ExpertFormRequireResponseDTO> getList(List<Long> categoryIds) {
        List<ExpertFormRequire> expertFormRequireList = expertFormRequireRepository.findByCategoryIdInAndStatus(categoryIds);

        return expertFormRequireList.stream().map(ExpertFormRequireMapper::toDto).toList();
    }

    @Override
    public List<ExpertFormRequireResponseDTO> getList(Long categoryId) {
        List<ExpertFormRequire> expertFormRequireList = expertFormRequireRepository.findByCategoryId(categoryId);

        return expertFormRequireList.stream().map(ExpertFormRequireMapper::toDto).toList();
    }
}
