package com.example.gcj.service;

import com.example.gcj.dto.blog.BlogListResponseDTO;
import com.example.gcj.dto.blog.BlogResponseDTO;
import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.other.PageResponseDTO;

public interface BlogService {
    void createBlog(CreateBlogDTO request);
    PageResponseDTO<BlogListResponseDTO> blogList(int pageNumber, int pageSize);
    BlogResponseDTO getBlog(long id);
}
