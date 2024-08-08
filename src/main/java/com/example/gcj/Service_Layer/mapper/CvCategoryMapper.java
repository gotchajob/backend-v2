package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.Repository_Layer.model.CvCategory;

public class CvCategoryMapper {
    public static CvCategoryListResponseDTO toDto(CvCategory cvCategory) {
        if (cvCategory == null) {
            return null;
        }

        return CvCategoryListResponseDTO
                .builder()
                .id(cvCategory.getId())
                .name(cvCategory.getName())
                .description(cvCategory.getDescription())
                .image(cvCategory.getImage())
                .icon(cvCategory.getIcon())
                .build();
    }
}
