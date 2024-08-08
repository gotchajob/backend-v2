package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Category;
import com.example.gcj.Repository_Layer.repository.CategoryRepository;
import com.example.gcj.Service_Layer.dto.category.CategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.category.CreateCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.category.UpdateCategoryRequestDTO;
import com.example.gcj.Service_Layer.service.CategoryService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryListResponseDTO> getAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryMapper::toDto).toList();
    }

    @Override
    public boolean delete(long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new CustomException("not found category");
        }

        category.setStatus(0);
        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(long id, UpdateCategoryRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (request.getName() == null) {
            throw new CustomException("name cannot be null");
        }

        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new CustomException("not found");
        }

        category.setName(request.getName());
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean create(CreateCategoryRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }
        if (request.getName() == null) {
            throw new CustomException("name cannot be null");
        }

        Category category = Category.builder().name(request.getName()).status(1).build();
        categoryRepository.save(category);

        return true;
    }
}
