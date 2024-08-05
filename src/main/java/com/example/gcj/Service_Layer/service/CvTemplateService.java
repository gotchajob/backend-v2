package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_template.CreateCvTemplateRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_template.CvTemplateListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_template.CvTemplateResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_template.UpdateCvTemplateRequestDTO;

import java.util.List;

public interface CvTemplateService {
    List<CvTemplateListResponseDTO> getList(Long cvCategoryId);

    CvTemplateResponseDTO getById(long id);

    boolean create(long userId, CreateCvTemplateRequestDTO request);

    boolean update(long id, UpdateCvTemplateRequestDTO request);

    boolean delete(long id);
}
