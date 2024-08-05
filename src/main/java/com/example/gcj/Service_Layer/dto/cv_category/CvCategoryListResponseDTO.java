package com.example.gcj.Service_Layer.dto.cv_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CvCategoryListResponseDTO {
    private long id;
    private String name;
    private String description;
    private String image;
    private String icon;
}
