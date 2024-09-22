package com.example.gcj.Service_Layer.dto.cv_category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCvCategoryRequestDTO {
    @NotBlank
    private String name;
    private String description;
    private String image;
    private String icon;
}
