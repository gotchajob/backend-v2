package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.BlogCategory;
import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryListResponseDTO;

public class BlogCategoryMapper {
    public static BlogCategoryListResponseDTO toDto(BlogCategory blogCategory) {
        if (blogCategory == null) {
            return null;
        }

        return BlogCategoryListResponseDTO
                .builder()
                .id(blogCategory.getId())
                .category(blogCategory.getCategory())
                .description(blogCategory.getDescription())
                .build();
    }
}
