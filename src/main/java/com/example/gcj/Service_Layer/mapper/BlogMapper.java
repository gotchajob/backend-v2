package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.blog.BlogListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.BlogResponseDTO;
import com.example.gcj.Repository_Layer.model.Blog;
import com.example.gcj.Service_Layer.dto.user.UserProfileDTO;

import java.util.List;

public class BlogMapper {

    public static BlogListResponseDTO toDtoList(Blog blog, UserProfileDTO authorInfo) {
        return BlogListResponseDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .thumbnail(blog.getThumbnail())
                .shortDescription(blog.getShortDescription())
                .createdAt(blog.getCreatedAt())
                .categoryId(blog.getCategory().getId())
                .category(blog.getCategory().getCategory())
                .profile(authorInfo)
                .build();
    }

    public static BlogResponseDTO toDto(Blog blog, List<Blog> relateBlog) {
        return BlogResponseDTO.builder()
                .id(blog.getId())
                .shortDescription(blog.getShortDescription())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .likes(null)
                .category(blog.getCategory().getCategory())
                .build();
    }



}
