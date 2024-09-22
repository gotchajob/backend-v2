package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.CvCategory;
import com.example.gcj.Repository_Layer.repository.CvCategoryRepository;
import com.example.gcj.Service_Layer.dto.cv_category.CreateCvCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.Service_Layer.service.CvCategoryService;
import com.example.gcj.Service_Layer.service.ShareService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.CvCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvCategoryImpl implements CvCategoryService, ShareService<CvCategory> {
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
                .status(1)
                .build();
        save(build);

        return true;
    }

    @Override
    public boolean delete(long id) {
        CvCategory cvCategory = get(id);

        cvCategory.setStatus(0);
        save(cvCategory);

        return true;
    }

    @Override
    public CvCategoryListResponseDTO getById(long id) {
        CvCategory cvCategory = get(id);

        return CvCategoryMapper.toDto(cvCategory);
    }

    @Override
    public CvCategory get(long id) {
        CvCategory cvCategory = cvCategoryRepository.findById(id);
        if (cvCategory == null) {
            throw new CustomException("not found cv category with id " + id);
        }

        return cvCategory;
    }

    @Override
    public CvCategory save(CvCategory entity) {
        if (entity == null) {
            return null;
        }

        return cvCategoryRepository.save(entity);
    }
}
