package com.example.gcj.service.impl;

import com.example.gcj.dto.cv.CVListResponseDTO;
import com.example.gcj.dto.cv.CreateCvRequestDTO;
import com.example.gcj.dto.cv.CvResponseDTO;
import com.example.gcj.dto.cv.UpdateCvRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Cv;
import com.example.gcj.model.CvCategory;
import com.example.gcj.repository.CvCategoryRepository;
import com.example.gcj.repository.CvRepository;
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
    private final CvCategoryRepository cvCategoryRepository;


    @Override
    public List<CVListResponseDTO> getByCategoryId(long categoryId) {
        List<Cv> cvs = cvRepository.findByCategoryId(categoryId);
        return cvs.stream().map(CvMapper::toDto).toList();
    }

    @Override
    public List<CVListResponseDTO> getByUserId(Long userId) {
        List<Cv> cvs = cvRepository.findByUserId(userId);
        return cvs.stream().map(CvMapper::toDto).toList();
    }

    @Override
    public CvResponseDTO getById(long id) {
        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        //todo: if user id == null don't need token, other need user token, and check it
        CvCategory cvCategory = cvCategoryRepository.findById(cv.getCategoryId());
        if (cvCategory == null) {
            throw new CustomException("not found cv category with id " + cv.getCategoryId());
        }

        return CvResponseDTO
                .builder()
                .id(cv.getId())
                .categoryId(cv.getCategoryId())
                .categoryName(cvCategory.getName())
                .categoryDescription(cvCategory.getDescription())
                .name(cv.getName())
                .cv(cv.getCv())
                .status(cv.getStatus())
                .createdAt(cv.getCreatedAt())
                .updatedAt(cv.getUpdatedAt())
                .build();
    }

    @Override
    public boolean create(Long userId, CreateCvRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        if (!cvCategoryRepository.existsById(request.getCvCategoryId())) {
            throw new CustomException("not found cv category with id " + request.getCvCategoryId());
        }

        Cv build = Cv
                .builder()
                .userId(userId)
                .categoryId(request.getCvCategoryId())
                .name(request.getName())
                .image(request.getImage())
                .cv(request.getCv())
                .status(Status.ACTIVE)
                .build();
        cvRepository.save(build);
        return true;
    }

    @Override
    public boolean update(long id, UpdateCvRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        //todo: check role to update
        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }

        cv.setCv(request.getCv());
        cv.setName(request.getName());
        cv.setImage(request.getImage());
        cvRepository.save(cv);

        return true;
    }

    @Override
    public boolean delete(long id) {
        Cv cv = cvRepository.findById(id);
        if (cv == null) {
            throw new CustomException("not found cv with id " + id);
        }
        //todo: check role
        cv.setStatus(Status.INACTIVE);
        cvRepository.save(cv);

        return true;
    }

    @Override
    public List<CVListResponseDTO> getTemplateByCategoryId(Long categoryId) {
        List<Cv> cvs = categoryId == null ?cvRepository.getTemplate() : cvRepository.getTemplate(categoryId);
        return cvs.stream().map(CvMapper::toDto).toList();
    }
}
