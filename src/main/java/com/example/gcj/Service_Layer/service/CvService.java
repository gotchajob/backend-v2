package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface CvService {
    List<CVListResponseDTO> getByCustomerId(long customerId);
    CvResponseDTO getById(Long customerId, long id);
    CreateCvResponseDTO create(long customerId, CreateCvRequestDTO request);

    boolean update(Long customerId, long id, UpdateCvRequestDTO request);

    boolean delete(Long customerId, long id);

    boolean updateToShare(long customerId, long id);

    PageResponseDTO<CVListResponseDTO> getShare(int pageNumber, int pageSize, String sortBy, String[] search);
}
