package com.example.gcj.Service_Layer.dto.expert_form_require;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateExpertFormRequireRequestDTO {
    @Min(1)
    private Long categoryId;
    @NotBlank
    private String name;
    private String description;
}
