package com.example.gcj.Service_Layer.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryListResponseDTO {
    private String name;
    private long id;
}
