package com.example.gcj.Service_Layer.dto.policy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyListResponseDTO {
    private long id;
    private String key;
    private String value;
    private String description;

}
