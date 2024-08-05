package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.cv_template.CvTemplateListResponseDTO;
import com.example.gcj.Repository_Layer.model.CvTemplate;

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
