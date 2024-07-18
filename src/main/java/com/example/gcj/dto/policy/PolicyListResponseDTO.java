package com.example.gcj.dto.policy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyListResponseDTO {
    private long id;
    private String key;
    private String value;
}
