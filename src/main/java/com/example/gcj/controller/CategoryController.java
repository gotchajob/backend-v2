package com.example.gcj.controller;

import com.example.gcj.model.Category;
import com.example.gcj.service.CategoryService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category Controller")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public Response<List<Category>> getAll() {
            List<Category> list = categoryService.getAll();
            return Response.ok(list);
    }
}
