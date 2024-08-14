package com.example.gcj.Service_Layer.dto.blog_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogCategoryListResponseDTO {
    private long id;
    private String category;
    private String description;
}
