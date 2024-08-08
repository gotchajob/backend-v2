package com.example.gcj.Service_Layer.dto.cv_template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class CvTemplateListDetailResponseDTO {
    private long id;
    private long cvCategoryId;
    private String cvCategoryName;
    private String name;
    private String image;
    private int status;
    private long numberUse;
    private Date createdAt;
}
