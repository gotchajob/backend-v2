package com.example.gcj.dto.expert_form_require;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class CreateExpertFormRequireRequestDTO implements Serializable {
    private Long categoryId;
    private String name;
    private String description;
}
