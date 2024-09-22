package com.example.gcj.Service_Layer.dto.blog_category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBlogCategoryRequestDTO {
    @NotBlank
    private String category;
    private String description;
}
