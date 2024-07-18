package com.example.gcj.dto.policy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePolicyRequestDTO {
    private String key;
    private String value;
    private String description;
}
