package com.example.gcj.service.impl;

import com.example.gcj.dto.cv_category.CreateCvCategoryRequestDTO;
import com.example.gcj.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.CvCategory;
import com.example.gcj.repository.CvCategoryRepository;
import com.example.gcj.service.CvCategoryService;
import com.example.gcj.util.mapper.CvCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvCategoryImpl implements CvCategoryService {
    private final CvCategoryRepository cvCategoryRepository;

    @Override
    public List<CvCategoryListResponseDTO> get() {
        List<CvCategory> cvCategories = cvCategoryRepository.findAll();

        return cvCategories.stream().map(CvCategoryMapper::toDto).toList();
    }

    @Override
    public boolean create(CreateCvCategoryRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        CvCategory build = CvCategory
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .icon(request.getIcon())
                .build();
        cvCategoryRepository.save(build);
        return true;
    }

    @Override
    public boolean delete(long id) {
        if (!cvCategoryRepository.existsById(id)) {
            throw new CustomException("not found cv category with id " + id);
        }

        cvCategoryRepository.deleteById(id);

        return true;
    }

    @Override
    public CvCategoryListResponseDTO getById(long id) {
        CvCategory cvCategory = cvCategoryRepository.findById(id);
        if (cvCategory == null) {
            throw new CustomException("not found cv category with id " + id);
        }

        return CvCategoryMapper.toDto(cvCategory);
    }
}
