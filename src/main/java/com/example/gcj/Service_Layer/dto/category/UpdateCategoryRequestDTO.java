package com.example.gcj.Service_Layer.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryRequestDTO {
    @NotBlank
    private String name;
}
