package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Category;
import com.example.gcj.Repository_Layer.repository.CategoryRepository;
import com.example.gcj.Service_Layer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        List<Category> list = categoryRepository.findAll();
        return list == null ? new ArrayList<>() : list;
    }
}
