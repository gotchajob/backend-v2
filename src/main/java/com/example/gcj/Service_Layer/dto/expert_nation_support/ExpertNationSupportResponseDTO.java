package com.example.gcj.Service_Layer.dto.expert_nation_support;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ExpertNationSupportResponseDTO implements Serializable {
    private long id;
    private String nation;
}
