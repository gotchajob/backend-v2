package com.example.gcj.service.impl;

import com.example.gcj.model.Category;
import com.example.gcj.repository.CategoryRepository;
import com.example.gcj.service.CategoryService;
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
