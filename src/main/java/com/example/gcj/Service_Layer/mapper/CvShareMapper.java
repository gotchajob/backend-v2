package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.CvShare;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;

public class CvShareMapper {
    public static CvShareListResponseDTO toDto(CvShare cvShare) {
        if (cvShare == null) {
            return null;
        }

        return CvShareListResponseDTO
                .builder()
                .id(cvShare.getId())
                .cvImage(cvShare.getCvImage())
                .categoryId(cvShare.getCategoryId())
                .category("coming soon!")
                .caption(cvShare.getCaption())
                .createdAt(cvShare.getCreatedAt())
                .customerId(cvShare.getCustomerId())
                .status(cvShare.getStatus())
                .build();
    }
}
