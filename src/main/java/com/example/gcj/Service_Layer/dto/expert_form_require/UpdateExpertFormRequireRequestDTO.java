package com.example.gcj.Service_Layer.dto.expert_form_require;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateExpertFormRequireRequestDTO {
    private Long categoryId;
    private String name;
    private String description;
}
