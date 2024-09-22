package com.example.gcj.Service_Layer.dto.policy;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePolicyRequestDTO {
    @NotBlank
    private String key;
    @NotBlank
    private String value;
    private String description;
}
