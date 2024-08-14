package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface CvShareService {
    boolean create(CreateCvShareRequestDTO request);

    boolean update(long id, UpdateCvShareRequestDTO request);

    boolean delete(long id);

    PageResponseDTO<CvShareListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search);
}
