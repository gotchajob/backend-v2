package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.category.CategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.category.CreateCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.category.UpdateCategoryRequestDTO;
import com.example.gcj.Service_Layer.service.CategoryService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public Response<List<CategoryListResponseDTO>> getAll() {
        List<CategoryListResponseDTO> list = categoryService.getAll();
        return Response.ok(list);
    }

    @PostMapping("")
    @Operation(description = "")
    public Response<String> create(
            @RequestBody @Valid CreateCategoryRequestDTO request
    ) {
        categoryService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateCategoryRequestDTO request
    ) {
        categoryService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {
        categoryService.delete(id);
        return Response.ok(null);
    }

}
