package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.blog.BlogListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.BlogResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.CreateBlogDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface BlogService {
    void createBlog(CreateBlogDTO request);
    PageResponseDTO<BlogListResponseDTO> blogList(Long categoryId, int pageNumber, int pageSize);
    BlogResponseDTO getBlog(long id);
    List<BlogListResponseDTO> findByCategoryId(long categoryId, int limit);
}
