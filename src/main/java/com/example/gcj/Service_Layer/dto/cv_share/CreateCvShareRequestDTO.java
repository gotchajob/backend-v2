package com.example.gcj.Service_Layer.dto.cv_share;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCvShareRequestDTO {
    private long cvId;
    private String caption;
}