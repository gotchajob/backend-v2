package com.example.gcj.service;

import com.example.gcj.dto.cv.*;

import java.util.List;

public interface CvService {
    List<CVListResponseDTO> getByUserId(Long userId);
    CvResponseDTO getById(Long userId, long id);
    CreateCvResponseDTO create(Long userId, CreateCvRequestDTO request);

    boolean update(Long userId,long id, UpdateCvRequestDTO request);

    boolean delete(Long userId, long id);
}
