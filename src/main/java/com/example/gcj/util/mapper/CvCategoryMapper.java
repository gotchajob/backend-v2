package com.example.gcj.util.mapper;

import com.example.gcj.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.model.BlogComment;
import com.example.gcj.model.CvCategory;

public class CvCategoryMapper {
    public static CvCategoryListResponseDTO toDto(CvCategory cvCategory) {
        if (cvCategory == null) {
            return null;
        }

        return CvCategoryListResponseDTO
                .builder()
                .id(cvCategory.getId())
                .name(cvCategory.getName())
                .description(cvCategory.getDescription())
                .image(cvCategory.getImage())
                .icon(cvCategory.getIcon())
                .build();
    }
}
