package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BlogCategory;
import com.example.gcj.Repository_Layer.repository.BlogCategoryRepository;
import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.CreateBlogCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.blog_category.UpdateBlogCategoryRequestDTO;
import com.example.gcj.Service_Layer.mapper.BlogCategoryMapper;
import com.example.gcj.Service_Layer.service.BlogCategoryService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCategoryServiceImpl implements BlogCategoryService {
    private final BlogCategoryRepository blogCategoryRepository;

    @Override
    public List<BlogCategoryListResponseDTO> get() {
        List<BlogCategory> all = blogCategoryRepository.findAll();
        return all.stream().map(BlogCategoryMapper::toDto).toList();
    }

    @Override
    public BlogCategoryResponseDTO getById(long id) {

        return null;
    }

    @Override
    public boolean create(CreateBlogCategoryRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        BlogCategory build = BlogCategory
                .builder()
                .category(request.getCategory())
                .description(request.getDescription())
                .build();
        blogCategoryRepository.save(build);

        return true;
    }

    @Override
    public boolean update(long id, UpdateBlogCategoryRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        BlogCategory blogCategory = get(id);
        blogCategory.setCategory(request.getCategory());
        blogCategory.setDescription(request.getDescription());
        blogCategoryRepository.save(blogCategory);

        return true;
    }

    @Override
    public boolean delete(long id) {
        get(id);
        
        blogCategoryRepository.deleteById(id);
        return true;
    }
    
    private BlogCategory get(long id) {
        BlogCategory blogCategory = blogCategoryRepository.findById(id);
        if (blogCategory == null) {
            throw new CustomException("not found blog category with id " + id);
        }
        
        return blogCategory;
    }
}
