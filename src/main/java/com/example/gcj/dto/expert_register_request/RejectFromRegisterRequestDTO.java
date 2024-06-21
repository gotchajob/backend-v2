package com.example.gcj.dto.expert_register_request;

import com.example.gcj.dto.expert_form_criteria.CreateExpertFormCriteriaRequestDTO;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class RejectFromRegisterRequestDTO implements Serializable {
    private String url;
    private String reasonReject;
    private List<CreateExpertFormCriteriaRequestDTO> criteriaList;
}
