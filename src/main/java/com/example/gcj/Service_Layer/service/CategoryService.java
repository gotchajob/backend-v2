package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.category.CategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.category.CreateCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.category.UpdateCategoryRequestDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryListResponseDTO> getAll();

    boolean delete(long id);

    boolean update(long id, UpdateCategoryRequestDTO request);

    boolean create(CreateCategoryRequestDTO request);
}
