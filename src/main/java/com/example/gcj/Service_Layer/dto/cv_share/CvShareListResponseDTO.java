package com.example.gcj.Service_Layer.dto.cv_share;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CvShareListResponseDTO {
    private long id;
    private String caption;
    private String cvImage;
    private long categoryId;
    private String category;
    private Date createdAt;
}
