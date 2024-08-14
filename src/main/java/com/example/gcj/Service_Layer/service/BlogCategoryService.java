package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.CreateBlogCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.blog_category.UpdateBlogCategoryRequestDTO;

import java.util.List;

public interface BlogCategoryService {
    List<BlogCategoryListResponseDTO> get();

    BlogCategoryResponseDTO getById(long id);

    boolean create(CreateBlogCategoryRequestDTO request);

    boolean update(long id, UpdateBlogCategoryRequestDTO request);

    boolean delete(long id);
}
