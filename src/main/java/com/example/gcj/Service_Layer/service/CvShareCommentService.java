package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_comment.CreateCvCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.CvCommentListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.CvCommentResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.UpdateCvCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface CvShareCommentService {
    PageResponseDTO<CvCommentListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String ...search);

    CvCommentResponseDTO getById(long id);

    boolean create(long customerId, CreateCvCommentRequestDTO request);

    boolean update(long id, UpdateCvCommentRequestDTO request);

    boolean delete(long id);
}
