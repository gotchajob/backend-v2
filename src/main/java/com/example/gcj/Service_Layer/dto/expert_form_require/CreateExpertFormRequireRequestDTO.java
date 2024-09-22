package com.example.gcj.Service_Layer.dto.expert_form_require;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class CreateExpertFormRequireRequestDTO implements Serializable {
    @Min(1)
    private Long categoryId;
    @NotBlank
    private String name;
    private String description;
}
