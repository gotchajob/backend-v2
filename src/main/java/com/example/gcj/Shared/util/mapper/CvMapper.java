package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.cv.CVListResponseDTO;
import com.example.gcj.Repository_Layer.model.Cv;

public class CvMapper {
    public static CVListResponseDTO toDto(Cv cv) {
        if (cv == null) {
            return null;
        }

        return CVListResponseDTO
                .builder()
                .id(cv.getId())
                .name(cv.getName())
                .image(cv.getImage())
                .status(cv.getStatus())
                .updatedAt(cv.getUpdatedAt())
                .build();
    }
}
