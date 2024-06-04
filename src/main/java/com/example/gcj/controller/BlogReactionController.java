package com.example.gcj.controller;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.dto.blog_reaction.CreateBlogReactionRequestDTO;
import com.example.gcj.dto.blog_reaction.UpdateBlogReactionDTO;
import com.example.gcj.model.BlogReaction;
import com.example.gcj.service.BlogReactionService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/blog-reaction")
@RequiredArgsConstructor
@Tag(name = "Blog Reaction Controller")
public class BlogReactionController {
    private final BlogReactionService blogReactionService;

    @PostMapping("/{idBlog}/reaction")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "role: user, admin, expert")
    public ResponseEntity<Response<String>> addBlogReaction(
            @RequestBody CreateBlogReactionRequestDTO request,
            @PathVariable("idBlog") long idBlog
    ) {
        blogReactionService.create(request, idBlog);
        return Response.success(null);
    }

    @PostMapping("/{idBlog}/reaction-detail")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "Get reaction detail")
    public ResponseEntity<Response<List<BlogReactionResponseDTO>>> getReactionByBlogAndUser(
            @RequestBody long userId,
            @PathVariable("idBlog") long idBlog
    ) {
       List<BlogReactionResponseDTO> list =  blogReactionService.findReactionByBlogIdAndUserId(idBlog, userId);
        return Response.success(list);
    }

    @GetMapping("/reaction")
    @Secured({Role.ADMIN})
    @Operation(description = "Get all blog reaction")
    public Response<List<BlogReactionResponseDTO>> getBlogReaction() {
        List<BlogReactionResponseDTO> list = blogReactionService.getAll();
        return Response.ok(list);
    }

    @PutMapping("/{id}/update")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "Update blog reaction")
    public Response<List<BlogReactionResponseDTO>> updateReaction(
            @RequestBody UpdateBlogReactionDTO request,
            @PathVariable("id") long idReaction

    ) {
        blogReactionService.updateBlogReaction(request, idReaction);
        return Response.ok(null);
    }

    @DeleteMapping("/{idReaction}/delete")
    @Secured({Role.USER, Role.ADMIN, Role.EXPERT})
    @Operation(description = "Delete blog reaction")
    public Response<List<BlogReactionResponseDTO>> deleteBlogReaction(
            @PathVariable("idReaction") long idReaction

    ) {
        blogReactionService.delteBlogReaction(idReaction);
        return Response.ok(null);
    }

}
