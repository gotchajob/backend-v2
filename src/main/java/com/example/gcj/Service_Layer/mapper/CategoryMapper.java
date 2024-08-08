package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.Category;
import com.example.gcj.Service_Layer.dto.category.CategoryListResponseDTO;

public class CategoryMapper {

    public static CategoryListResponseDTO toDto(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryListResponseDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
