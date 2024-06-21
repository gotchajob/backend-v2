package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCvRequestDTO {
    private String cv;
    private String name;
    private String image;
}
