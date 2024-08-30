package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.cv_share_comment.CreateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareCommentListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvCommentResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.UpdateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface CvShareCommentService {
    PageResponseDTO<CvShareCommentListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String ...search);

    CvCommentResponseDTO getById(long id);

    boolean create(long customerId, CreateCvShareCommentRequestDTO request);

    boolean update(long id, UpdateCvShareCommentRequestDTO request);

    boolean delete(long id);
}
