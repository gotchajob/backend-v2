package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateCvRequestDTO implements Serializable {
    private long cvTemplateId;
}
