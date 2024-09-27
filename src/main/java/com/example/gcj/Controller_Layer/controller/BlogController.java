package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.blog.BlogListResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.BlogResponseDTO;
import com.example.gcj.Service_Layer.dto.blog.CreateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.blog.UpdateBlogRequestDTO;
import com.example.gcj.Service_Layer.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.Service_Layer.dto.blog_comment.CreateBlogCommentDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.BlogCommentService;
import com.example.gcj.Service_Layer.service.BlogService;
import com.example.gcj.Service_Layer.service.StaffService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final BlogCommentService blogCommentService;
    private final StaffService staffService;

    @PostMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> createBlog(
            @RequestBody @Valid CreateBlogRequestDTO request
    ) {
        long currentStaffId = staffService.getCurrentStaffId();
        blogService.createBlog(request, currentStaffId);
        return Response.ok(null);
    }

    @GetMapping("")
    public Response<PageResponseDTO<BlogListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize,
            @RequestParam(required = false) @Min(1) Long categoryId
    ) {
        PageResponseDTO<BlogListResponseDTO> list = blogService.blogList(categoryId, pageNumber, pageSize);
        return Response.ok(list);
    }

    @GetMapping("/{id}")
    public Response<BlogResponseDTO> get(
            @PathVariable @Min(1) long id
    ) {
        BlogResponseDTO response = blogService.getBlog(id);
        return Response.ok(response);
    }

    @GetMapping("/{id}/comment")
    public Response<PageResponseDTO<BlogCommentListDTO>> getComment(
            @PathVariable @Min(1) long id,
            @RequestParam(required = false) Long parentCommentId,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize
    ) {
        PageResponseDTO<BlogCommentListDTO> response = blogCommentService.get(id, parentCommentId, pageNumber, pageSize);
        return Response.ok(response);
    }

    @PostMapping("/{id}/comment")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "role: user, admin, expert")
    public Response<String> addComment(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid CreateBlogCommentDTO request
    ) {
        blogCommentService.create(request, id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> updateBlog(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateBlogRequestDTO request
    ) {
        blogService.update(id, request);
        return Response.ok(null);
    }



}
