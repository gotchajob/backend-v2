package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CreateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareCommentListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvCommentResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.UpdateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.CvCommentMapper;
import com.example.gcj.Service_Layer.service.CvShareCommentService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvShareCommentServiceImpl implements CvShareCommentService {
    private final CvShareCommentRepository cvShareCommentRepository;
    private final CvShareRepository cvShareRepository;
    private final SearchRepository searchRepository;
    private final CustomerRepository customerRepository;

    @Override
    public PageResponseDTO<CvShareCommentListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String ...search) {
        Page<CvShareComment> page = searchRepository.getEntitiesPage(CvShareComment.class, pageNumber, pageSize, sortBy, search);

        List<CvShareCommentListResponseDTO> cvShareCommentListResponseDTOList = page.map(CvCommentMapper::toDto).toList();
        for (CvShareCommentListResponseDTO r : cvShareCommentListResponseDTOList) {
            r.setUseInfo(customerRepository.getUserInfo(r.getCustomerId()));
        }

        return new PageResponseDTO<>(cvShareCommentListResponseDTOList, page.getTotalPages());
    }

    @Override
    public CvCommentResponseDTO getById(long id) {
        return null;
    }

    @Override
    public boolean create(long customerId, CreateCvShareCommentRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (!cvShareRepository.existsById(request.getCvShareId())) {
            throw new CustomException("not found cv share");
        }

        CvShareComment build = CvShareComment
                .builder()
                .customerId(customerId)
                .cvShareId(request.getCvShareId())
                .status(1)
                .comment(request.getComment())
                .rating(request.getRating())
                .build();
        cvShareCommentRepository.save(build);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCvShareCommentRequestDTO request) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (!cvShareCommentRepository.existsById(id)) {
            throw new CustomException("not found cv share comment");
        }

        cvShareCommentRepository.findById(id);
        return true;
    }
}
