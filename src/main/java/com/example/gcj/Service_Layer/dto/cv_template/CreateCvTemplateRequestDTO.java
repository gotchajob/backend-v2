package com.example.gcj.Service_Layer.dto.cv_template;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateCvTemplateRequestDTO implements Serializable {
    @Min(1)
    private long cvCategoryId;
    @NotBlank
    private String templateJson;
    @NotBlank
    private String name;
    @NotBlank
    private String image;

}
