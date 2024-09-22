package com.example.gcj.Service_Layer.dto.cv;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCvRequestDTO {
    @NotBlank
    private String cv;
    @NotBlank
    private String name;
    @NotBlank
    private String image;
}
