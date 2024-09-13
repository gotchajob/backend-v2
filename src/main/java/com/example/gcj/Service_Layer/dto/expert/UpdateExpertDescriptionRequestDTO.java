package com.example.gcj.Service_Layer.dto.expert;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateExpertDescriptionRequestDTO {
    @NotBlank(message = "shortDescription cannot be null or blank")
    private String shortDescription;
}
