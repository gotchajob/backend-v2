package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_template.*;

import java.util.List;

public interface CvTemplateService {
    List<CvTemplateListResponseDTO> getList(Long cvCategoryId);

    CvTemplateResponseDTO getById(long id);

    boolean create(long userId, CreateCvTemplateRequestDTO request);

    boolean update(long id, UpdateCvTemplateRequestDTO request);

    boolean delete(long id);

    List<CvTemplateListDetailResponseDTO> getListForStaff(Long categoryId, Integer status);
}
