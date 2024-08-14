package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import com.example.gcj.Repository_Layer.repository.CvShareCommentRepository;
import com.example.gcj.Repository_Layer.repository.SearchRepository;
import com.example.gcj.Service_Layer.dto.cv_comment.CreateCvCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.CvCommentListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.CvCommentResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_comment.UpdateCvCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.CvCommentMapper;
import com.example.gcj.Service_Layer.service.CvShareCommentService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CvShareCommentServiceImpl implements CvShareCommentService {
    private final CvShareCommentRepository cvShareCommentRepository;
    private final SearchRepository searchRepository;

    @Override
    public PageResponseDTO<CvCommentListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String ...search) {
        Page<CvShareComment> page = searchRepository.getEntitiesPage(CvShareComment.class, pageNumber, pageSize, sortBy, search);

        return new PageResponseDTO<>(page.map(CvCommentMapper::toDto).toList(), page.getTotalPages());
    }

    @Override
    public CvCommentResponseDTO getById(long id) {
        return null;
    }

    @Override
    public boolean create(long customerId, CreateCvCommentRequestDTO request) {
        CvShareComment build = CvShareComment
                .builder()
                .customerId(customerId)
                .cvShareId(request.getCvShareId())
                .status(1)
                .comment(request.getComment())
                .build();
        cvShareCommentRepository.save(build);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCvCommentRequestDTO request) {
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
