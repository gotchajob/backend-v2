package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Cv;
import com.example.gcj.Repository_Layer.model.CvTemplate;
import com.example.gcj.Repository_Layer.repository.CvRepository;
import com.example.gcj.Repository_Layer.repository.CvTemplateRepository;
import com.example.gcj.Repository_Layer.repository.SearchRepository;
import com.example.gcj.Service_Layer.dto.cv.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.CvService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.CvMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    private final SearchRepository searchRepository;
    private final CvTemplateRepository cvTemplateRepository;
    private final CustomerService customerService;

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

        int maxCv = customerService.getMaxCv(customerId);
        long numberCv = cvRepository.countByStatusNotAndCustomerId(0, customerId);
        if (numberCv >= maxCv) {
            throw new CustomException("You have reached your CV creation limit");
        }

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

    @Override
    public boolean updateToShare(long customerId, long id) {
        Cv cv = get(id);

        if (cv.getStatus() == 1) {
            cv.setStatus(2);
        } else if (cv.getStatus() == 2) {
            cv.setStatus(1);
        }

        cvRepository.save(cv);
        return true;
    }

    @Override
    public PageResponseDTO<CVListResponseDTO> getShare(int pageNumber, int pageSize, String sortBy, String[] search) {
        String newSearch = "status=2";
        if (search == null) {
            search = new String[0];
        }

        search = Arrays.copyOf(search, search.length + 1);
        search[search.length - 1] = newSearch;

        Page<Cv> cvPage = searchRepository.getEntitiesPage(Cv.class, pageNumber, pageSize, sortBy, search);

        return new PageResponseDTO<>(cvPage.map(CvMapper::toDto).stream().toList(), cvPage.getTotalPages());
    }

    private Cv get(long id) {
        Cv cv = cvRepository.getById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        return cv;
    }
}
