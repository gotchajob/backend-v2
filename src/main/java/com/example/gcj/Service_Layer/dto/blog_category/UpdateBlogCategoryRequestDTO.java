package com.example.gcj.Service_Layer.dto.blog_category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBlogCategoryRequestDTO {
    private String category;
    private String description;
}