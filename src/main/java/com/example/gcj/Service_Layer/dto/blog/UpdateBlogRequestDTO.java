package com.example.gcj.Service_Layer.dto.blog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBlogRequestDTO {
    private String thumbnail;
    @NotBlank
    private String title;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String content;
    @Min(1)
    private long categoryId;
}
