package com.example.gcj.service;

import com.example.gcj.dto.blog.BlogListResponseDTO;
import com.example.gcj.dto.blog.BlogResponseDTO;
import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.other.PageResponseDTO;

import java.util.List;

public interface BlogService {
    void createBlog(CreateBlogDTO request);
    PageResponseDTO<BlogListResponseDTO> blogList(Long categoryId, int pageNumber, int pageSize);
    BlogResponseDTO getBlog(long id);
    List<BlogListResponseDTO> findByCategoryId(long categoryId, int limit);
}
