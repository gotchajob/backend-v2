package com.example.gcj.Service_Layer.dto.cv_template;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UpdateCvTemplateRequestDTO implements Serializable {
    @NotBlank
    private String templateJson;
    @NotBlank
    private String name;
    @NotBlank
    private String image;
}
