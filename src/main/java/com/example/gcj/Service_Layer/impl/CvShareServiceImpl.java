package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Cv;
import com.example.gcj.Repository_Layer.model.CvShare;
import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import com.example.gcj.Service_Layer.mapper.CvShareMapper;
import com.example.gcj.Service_Layer.service.CvShareService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvShareServiceImpl implements CvShareService {
    private final CvShareRepository cvShareRepository;
    private final CvShareCommentRepository cvShareCommentRepository;
    private final CvRepository cvRepository;
    private final CustomerRepository customerRepository;
    private final SearchRepository searchRepository;

    @Override
    public boolean create(CreateCvShareRequestDTO request, long customerId) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        Cv cv = cvRepository.getById(request.getCvId());
        if (cv == null) {
            throw new CustomException("not found cv");
        }

        if (cv.getCustomerId() != customerId) {
            throw new CustomException("cv is not yours");
        }

        CvShare cvShare = CvShare
                .builder()
                .cvId(cv.getId())
                .customerId(cv.getCustomerId())
                .cvImage(cv.getImage())
                .caption(request.getCaption())
                .categoryId(0)
                .status(cv.getStatus())
                .build();
        save(cvShare);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCvShareRequestDTO request, long customerId) {
        CvShare cvShare = get(id);
        checkAuthor(customerId, cvShare);

        cvShare.setCaption(request.getCaption());
        save(cvShare);

        return true;
    }

    @Override
    public boolean delete(long id, long customerId) {
        CvShare cvShare = get(id);
        checkAuthor(customerId, cvShare);

        cvShare.setStatus(0);
        save(cvShare);

        return true;
    }

    @Override
    public PageResponseDTO<CvShareListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search) {
        Page<CvShare> entitiesPage = searchRepository.getEntitiesPage(CvShare.class, pageNumber, pageSize, sortBy, search);

        List<CvShareListResponseDTO> responseList = entitiesPage.map(CvShareMapper::toDto).toList();
        for (CvShareListResponseDTO r : responseList) {
            r.setUserInfo(customerRepository.getUserInfo(r.getCustomerId()));

            List<CvShareRatingListResponseDTO> ratingByCvShareId = cvShareCommentRepository.getRatingByCvShareId(r.getId());
            r.setRating(ratingByCvShareId);
        }

        return new PageResponseDTO<>(responseList, entitiesPage.getTotalPages());
    }

    @Override
    public boolean updateStatus(long id, int status, long customerId) {
        CvShare cvShare = get(id);
        checkAuthor(customerId, cvShare);

        if (cvShare.getStatus() == status) {
            return false;
        }

        cvShare.setStatus(status);
        save(cvShare);

        return true;
    }

    @Override
    public CvShareResponseDTO getById(long id, long customerId) {
        CvShare cvShare = get(id);
        checkAuthor(customerId, cvShare);

        List<CvShareRatingListResponseDTO> rating = cvShareCommentRepository.getRatingByCvShareId(cvShare.getId());
        UserInfoResponseDTO userInfo = customerRepository.getUserInfo(cvShare.getCustomerId());

        return CvShareResponseDTO
                .builder()
                .id(cvShare.getId())
                .caption(cvShare.getCaption())
                .cvImage(cvShare.getCvImage())
                .categoryId(cvShare.getCategoryId())
                .category("coming soon")
                .createdAt(cvShare.getCreatedAt())
                .rating(rating)
                .userInfo(userInfo)
                .build();
    }

    @Override
    public List<CvShareRatingListResponseDTO> getRating(long id) {

        return cvShareCommentRepository.getRatingByCvShareId(id);
    }

    private CvShare save(CvShare cvShare) {
        if (cvShare == null) {
            return null;
        }

        return cvShareRepository.save(cvShare);
    }

    private CvShare get(long id) {
        CvShare cvShare = cvShareRepository.findById(id);
        if (cvShare == null) {
            throw new CustomException("not found cv share");
        }

        return cvShare;
    }

    private boolean checkAuthor(long customerId, CvShare cvShare) {
        if (cvShare == null) {
            return false;
        }

        if (cvShare.getCustomerId() != customerId) {
            throw new CustomException("current customer is not same with customer in cv share");
        }

        return true;
    }
}
