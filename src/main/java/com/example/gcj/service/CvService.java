package com.example.gcj.service;

import com.example.gcj.dto.cv.CVListResponseDTO;
import com.example.gcj.dto.cv.CreateCvRequestDTO;
import com.example.gcj.dto.cv.CvResponseDTO;
import com.example.gcj.dto.cv.UpdateCvRequestDTO;

import java.util.List;

public interface CvService {
    List<CVListResponseDTO> getByCategoryId(long categoryId);
    List<CVListResponseDTO> getByUserId(Long userId);
    CvResponseDTO getById(long id);


    boolean create(Long userId, CreateCvRequestDTO request);

    boolean update(long id, UpdateCvRequestDTO request);

    boolean delete(long id);

    List<CVListResponseDTO> getTemplateByCategoryId(Long categoryId);
}
