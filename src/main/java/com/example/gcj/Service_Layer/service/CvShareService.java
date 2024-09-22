package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface CvShareService {
    boolean create(CreateCvShareRequestDTO request, long customerId);

    boolean update(long id, UpdateCvShareRequestDTO request, long customerId);

    boolean delete(long id, long customerId);

    PageResponseDTO<CvShareListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search);

    boolean updateStatus(long id, int i, long customerId);

    CvShareResponseDTO getById(long id, long customerId);

    List<CvShareRatingListResponseDTO> getRating(long id);
}
