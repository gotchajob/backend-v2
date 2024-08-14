package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Cv;
import com.example.gcj.Repository_Layer.model.CvShare;
import com.example.gcj.Repository_Layer.repository.CvRepository;
import com.example.gcj.Repository_Layer.repository.CvShareRepository;
import com.example.gcj.Repository_Layer.repository.SearchRepository;
import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.CvShareMapper;
import com.example.gcj.Service_Layer.service.CvShareService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CvShareServiceImpl implements CvShareService {
    private final CvShareRepository cvShareRepository;
    private final CvRepository cvRepository;
    private final SearchRepository searchRepository;

    @Override
    public boolean create(CreateCvShareRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        Cv cv = cvRepository.getById(request.getCvId());
        if (cv == null) {
            throw new CustomException("not found cv");
        }

        CvShare cvShare = CvShare
                .builder()
                .cvId(cv.getId())
                .cvImage(cv.getImage())
                .caption(request.getCaption())
                .categoryId(0)
                .status(cv.getStatus())
                .build();
        save(cvShare);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCvShareRequestDTO request) {
        CvShare cvShare = get(id);

        cvShare.setCaption(request.getCaption());
        save(cvShare);

        return true;
    }

    @Override
    public boolean delete(long id) {
        CvShare cvShare = get(id);

        cvShare.setStatus(0);
        save(cvShare);

        return true;
    }

    @Override
    public PageResponseDTO<CvShareListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search) {
        Page<CvShare> entitiesPage = searchRepository.getEntitiesPage(CvShare.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(entitiesPage.map(CvShareMapper::toDto).toList(), entitiesPage.getTotalPages());
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
}
