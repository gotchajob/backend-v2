package com.example.gcj.service;

import com.example.gcj.dto.cv_category.CreateCvCategoryRequestDTO;
import com.example.gcj.dto.cv_category.CvCategoryListResponseDTO;

import java.util.List;

public interface CvCategoryService {

    List<CvCategoryListResponseDTO> get();

    boolean create(CreateCvCategoryRequestDTO request);

    boolean delete(long id);

    CvCategoryListResponseDTO getById(long id);
}
