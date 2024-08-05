package com.example.gcj.Service_Layer.dto.cv;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCvRequestDTO {
    private String cv;
    private String name;
    private String image;
}
