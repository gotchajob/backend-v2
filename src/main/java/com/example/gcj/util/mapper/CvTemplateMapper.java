package com.example.gcj.util.mapper;

import com.example.gcj.dto.cv_template.CvTemplateListResponseDTO;
import com.example.gcj.model.CvTemplate;

public class CvTemplateMapper {
    public static CvTemplateListResponseDTO toDto(CvTemplate cvTemplate) {
        if (cvTemplate == null) return null;

        return CvTemplateListResponseDTO
                .builder()
                .id(cvTemplate.getId())
                .name(cvTemplate.getName())
                .image(cvTemplate.getImage())
                .cvCategoryId(cvTemplate.getCategoryId())
                .build();
    }
}
