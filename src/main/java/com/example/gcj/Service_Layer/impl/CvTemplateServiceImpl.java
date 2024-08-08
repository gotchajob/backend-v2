package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.CvCategory;
import com.example.gcj.Repository_Layer.model.CvTemplate;
import com.example.gcj.Repository_Layer.repository.CvCategoryRepository;
import com.example.gcj.Repository_Layer.repository.CvTemplateRepository;
import com.example.gcj.Repository_Layer.repository.UserRepository;
import com.example.gcj.Service_Layer.dto.cv_template.*;
import com.example.gcj.Service_Layer.service.CvTemplateService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.CvTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CvTemplateServiceImpl implements CvTemplateService {
    private final CvTemplateRepository cvTemplateRepository;
    private final UserRepository userRepository;
    private final CvCategoryRepository cvCategoryRepository;


    @Override
    public List<CvTemplateListResponseDTO> getList(Long cvCategoryId) {
        List<CvTemplate> cvTemplateList = cvCategoryId == null
                ? cvTemplateRepository.findAll()
                : cvTemplateRepository.findByCategoryId(cvCategoryId);

        return cvTemplateList.stream().map(CvTemplateMapper::toDto).toList();
    }

    @Override
    public CvTemplateResponseDTO getById(long id) {
        CvTemplate cvTemplate = cvTemplateRepository.findById(id);
        if (cvTemplate == null || cvTemplate.getStatus() == 0) {
            throw new CustomException("not found cv template with id " + id);
        }

        CvCategory cvCategory = cvCategoryRepository.findById(cvTemplate.getCategoryId());

        return CvTemplateResponseDTO
                .builder()
                .id(cvTemplate.getId())
                .name(cvTemplate.getName())
                .templateJson(cvTemplate.getTemplateJson())
                .cvCategoryId(cvTemplate.getCategoryId())
                .cvCategoryName(cvCategory.getName())
                .cvCategoryDescription(cvCategory.getDescription())
                .image(cvTemplate.getImage())
                .build();
    }

    @Override
    public boolean create(long userId, CreateCvTemplateRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        if (!cvCategoryRepository.existsById(request.getCvCategoryId())) {
            throw new CustomException("not found cv category with id " + request.getCvCategoryId());
        }

        if (!userRepository.existsById(userId)) {
            throw new CustomException("not found user with id " + userId);
        }

        CvTemplate build = CvTemplate
                .builder()
                .name(request.getName())
                .templateJson(request.getTemplateJson())
                .categoryId(request.getCvCategoryId())
                .status(Status.ACTIVE)
                .image(request.getImage())
                .createdBy(userId)
                .build();
        cvTemplateRepository.save(build);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCvTemplateRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        CvTemplate cvTemplate = cvTemplateRepository.findById(id);
        if (cvTemplate == null) {
            throw new CustomException("not found cv template with id " + id);
        }

        cvTemplate.setTemplateJson(request.getTemplateJson());
        cvTemplate.setName(request.getName());
        cvTemplate.setImage(request.getImage());
        cvTemplateRepository.save(cvTemplate);

        return true;
    }

    @Override
    public boolean delete(long id) {
        CvTemplate cvTemplate = cvTemplateRepository.findById(id);
        if (cvTemplate == null) {
            throw new CustomException("not found cv template with id " + id);
        }

        cvTemplate.setStatus(Status.INACTIVE);
        cvTemplateRepository.save(cvTemplate);

        return true;
    }

    @Override
    public List<CvTemplateListDetailResponseDTO> getListForStaff(Long categoryId, Integer status) {
        List<CvTemplateListDetailResponseDTO> list = cvTemplateRepository.getAndCountNumberUse(categoryId, status);
        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }
}
