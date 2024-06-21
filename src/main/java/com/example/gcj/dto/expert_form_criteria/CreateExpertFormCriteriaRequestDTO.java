package com.example.gcj.dto.expert_form_criteria;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateExpertFormCriteriaRequestDTO implements Serializable {
    private String criteria;
    private String description;
    private int status;
}
