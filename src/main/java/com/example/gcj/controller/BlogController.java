package com.example.gcj.controller;

import com.example.gcj.dto.blog.BlogListResponseDTO;
import com.example.gcj.dto.blog.BlogResponseDTO;
import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.dto.blog_comment.CreateBlogCommentDTO;
import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.dto.blog_reaction.CreateBlogReactionRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.service.BlogCommentService;
import com.example.gcj.service.BlogReactionService;
import com.example.gcj.service.BlogService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
@Tag(name = "Blog Controller")
public class BlogController {
    private final BlogService blogService;
    private final BlogCommentService blogCommentService;
    private final BlogReactionService blogReactionService;

    @PostMapping("")
    @Secured(Role.ADMIN)
    @Operation(description = "role: admin")
    public ResponseEntity<Response<String>> createBlog(
            @RequestBody CreateBlogDTO request
    ) {
        try {
            blogService.createBlog(request);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Response<List<BlogListResponseDTO>>> getBlogByCategoryId(
            @RequestParam long categoryId,
            @RequestParam int limit
    ) {
        List<BlogListResponseDTO> blogs = blogService.findByCategoryId(categoryId, limit);
        return Response.success(blogs);
    }

    @GetMapping("")
    public ResponseEntity<Response<PageResponseDTO<BlogListResponseDTO>>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize
    ) {
        try {
            PageResponseDTO<BlogListResponseDTO> list = blogService.blogList(pageNumber, pageSize);
            return Response.success(list);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BlogResponseDTO>> get(
            @PathVariable long id
    ) {
        try {
            BlogResponseDTO response = blogService.getBlog(id);
            return Response.success(response);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<Response<PageResponseDTO<BlogCommentListDTO>>> getComment(
            @PathVariable long id,
            @RequestParam(required = false) Long parentCommentId,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize
    ) {
        try {
            PageResponseDTO<BlogCommentListDTO> response = blogCommentService.get(id, parentCommentId, pageNumber, pageSize);
            return Response.success(response);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("/{id}/comment")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "role: user, admin, expert")
    public ResponseEntity<Response<String>> addComment(
            @PathVariable long id,
            @RequestBody CreateBlogCommentDTO request
    ) {
        try {
            blogCommentService.create(request, id);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }


    @PostMapping("/{id}/reaction")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "role: user, admin, expert")
    public ResponseEntity<Response<String>> addBlogReaction(
            @RequestBody CreateBlogReactionRequestDTO request,
            @PathVariable("id") long id
    ) {
        blogReactionService.create(request, id);
        return Response.success(null);
    }

    @GetMapping("/reaction")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "role: user, admin, expert")
    public ResponseEntity<Response<List<BlogReactionResponseDTO>>> getBlogReaction() {
        blogReactionService.getAll();
        return Response.success(null);
    }
}
