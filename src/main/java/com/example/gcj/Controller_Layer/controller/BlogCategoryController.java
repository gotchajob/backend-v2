package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.BlogCategoryResponseDTO;
import com.example.gcj.Service_Layer.dto.blog_category.CreateBlogCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.blog_category.UpdateBlogCategoryRequestDTO;
import com.example.gcj.Service_Layer.service.BlogCategoryService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-category")
@RequiredArgsConstructor
public class BlogCategoryController { 
    private final BlogCategoryService blogCategoryService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<BlogCategoryListResponseDTO>> get(
    ) {
        List<BlogCategoryListResponseDTO> response = blogCategoryService.get();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "coming soon!")
    public Response<BlogCategoryResponseDTO> getById(
            @PathVariable long id
    ) {
        BlogCategoryResponseDTO response = blogCategoryService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateBlogCategoryRequestDTO request
    ) {
        blogCategoryService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateBlogCategoryRequestDTO request
    ) {
        blogCategoryService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        blogCategoryService.delete(id);
        return Response.ok(null);
    }
}
