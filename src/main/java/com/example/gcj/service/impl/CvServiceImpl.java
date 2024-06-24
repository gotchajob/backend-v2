package com.example.gcj.service.impl;

import com.example.gcj.dto.cv.*;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Cv;
import com.example.gcj.model.CvTemplate;
import com.example.gcj.repository.CvRepository;
import com.example.gcj.repository.CvTemplateRepository;
import com.example.gcj.service.CvService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.CvMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    private final CvTemplateRepository cvTemplateRepository;


    @Override
    public List<CVListResponseDTO> getByUserId(Long userId) {
        List<Cv> cvs = cvRepository.findByUserId(userId);
        return cvs.stream().map(CvMapper::toDto).toList();
    }

    @Override
    public CvResponseDTO getById(Long userId, long id) {
        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (userId != null && cv.getUserId() != userId) {
            throw new CustomException("cv is not yours");
        }

        return CvResponseDTO
                .builder()
                .id(cv.getId())
                .cvTemplateId(cv.getCvTemplateId())
                .name(cv.getName())
                .cv(cv.getCv())
                .status(cv.getStatus())
                .createdAt(cv.getCreatedAt())
                .updatedAt(cv.getUpdatedAt())
                .build();
    }

    @Override
    public CreateCvResponseDTO create(Long userId, CreateCvRequestDTO request) {
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
                .userId(userId)
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
    public boolean update(Long userId, long id, UpdateCvRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (userId != null && cv.getUserId() != userId) {
            throw new CustomException("cv is not yours");
        }

        cv.setCv(request.getCv());
        cv.setName(request.getName());
        cv.setImage(request.getImage());
        cvRepository.save(cv);

        return true;
    }

    @Override
    public boolean delete(Long userId, long id) {
        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        if (userId != null && cv.getUserId() != userId) {
            throw new CustomException("cv is not yours");
        }

        cv.setStatus(Status.INACTIVE);
        cvRepository.save(cv);

        return true;
    }
}
