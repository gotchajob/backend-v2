package com.example.gcj.Service_Layer.dto.cv_template;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CvTemplateListResponseDTO implements Serializable {
    private long id;
    private long cvCategoryId;
    private String name;
    private String image;
}
