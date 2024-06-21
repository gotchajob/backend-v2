package com.example.gcj.dto.expert_form_require;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ExpertFormRequireResponseDTO implements Serializable {
    private long id;
    private Long categoryId;
    private String name;
    private String description;
}
