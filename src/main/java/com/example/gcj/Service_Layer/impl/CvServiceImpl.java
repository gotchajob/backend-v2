package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Cv;
import com.example.gcj.Repository_Layer.model.CvTemplate;
import com.example.gcj.Repository_Layer.repository.CvRepository;
import com.example.gcj.Repository_Layer.repository.CvTemplateRepository;
import com.example.gcj.Service_Layer.dto.cv.*;
import com.example.gcj.Service_Layer.service.CvService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.CvMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    private final CvTemplateRepository cvTemplateRepository;


    @Override
    public List<CVListResponseDTO> getByCustomerId(long customerId) {
        List<Cv> cvs = cvRepository.findByCustomerId(customerId);
        return cvs.stream().map(CvMapper::toDto).toList();
    }

    @Override
    public CvResponseDTO getById(Long customerId, long id) {
        Cv cv = cvRepository.getById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (customerId != null && !cv.getCustomerId().equals(customerId)) {
            throw new CustomException("cv is not yours");
        }

        return CvResponseDTO
                .builder()
                .id(cv.getId())
                .cvTemplateId(cv.getCvTemplateId())
                .name(cv.getName())
                .cv(cv.getCv())
                .image(cv.getImage())
                .status(cv.getStatus())
                .createdAt(cv.getCreatedAt())
                .updatedAt(cv.getUpdatedAt())
                .build();
    }

    @Override
    public CreateCvResponseDTO create(long customerId, CreateCvRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        //todo: check number of cv
        CvTemplate cvTemplate = cvTemplateRepository.findById(request.getCvTemplateId());
        if (cvTemplate == null) {
            throw new CustomException("not found cv template with id " + request.getCvTemplateId());
        }

        Cv build = Cv
                .builder()
                .customerId(customerId)
                .cvTemplateId(request.getCvTemplateId())
                .name(cvTemplate.getName())
                .image(cvTemplate.getImage())
                .cv(cvTemplate.getTemplateJson())
                .status(Status.ACTIVE)
                .build();
        Cv save = cvRepository.save(build);

        return CreateCvResponseDTO.builder().id(save.getId()).build();
    }

    @Override
    public boolean update(Long customerId, long id, UpdateCvRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        Cv cv = cvRepository.getById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (customerId != null && !cv.getCustomerId().equals(customerId)) {
            throw new CustomException("cv is not yours");
        }

        cv.setCv(request.getCv());
        cv.setName(request.getName());
        cv.setImage(request.getImage());
        cvRepository.save(cv);

        return true;
    }

    @Override
    public boolean delete(Long customerId, long id) {
        Cv cv = cvRepository.getById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (customerId != null && !cv.getCustomerId().equals(customerId)) {
            throw new CustomException("cv is not yours");
        }

        cv.setStatus(Status.INACTIVE);
        cvRepository.save(cv);

        return true;
    }
}
