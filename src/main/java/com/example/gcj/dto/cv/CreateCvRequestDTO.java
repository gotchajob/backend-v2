package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateCvRequestDTO implements Serializable {
    private String name;
    private String cv;
    private long cvCategoryId;
    private String image;
}
