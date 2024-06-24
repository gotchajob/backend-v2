package com.example.gcj.dto.cv_template;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UpdateCvTemplateRequestDTO implements Serializable {
    private String templateJson;
    private String name;
    private String image;
}
