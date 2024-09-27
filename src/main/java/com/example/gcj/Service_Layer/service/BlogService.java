package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.blog.BlogListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.BlogResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.CreateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.blog.UpdateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

public interface BlogService {
    void createBlog(CreateBlogRequestDTO request, long currentStaffId);
    PageResponseDTO<BlogListResponseDTO> blogList(Long categoryId, int pageNumber, int pageSize);
    BlogResponseDTO getBlog(long id);

    boolean update(long id, UpdateBlogRequestDTO request);
}
