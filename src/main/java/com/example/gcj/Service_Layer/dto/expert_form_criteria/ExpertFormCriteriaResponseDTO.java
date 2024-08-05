package com.example.gcj.Service_Layer.dto.expert_form_criteria;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ExpertFormCriteriaResponseDTO implements Serializable {
    private long id;
    private Long expertRegisterRequestId;
    private String criteria;
    private String description;
    private int status;
}
