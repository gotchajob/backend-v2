package com.example.gcj.Service_Layer.dto.blog_category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBlogCategoryRequestDTO {
    @NotBlank
    private String category;
    private String description;
}
