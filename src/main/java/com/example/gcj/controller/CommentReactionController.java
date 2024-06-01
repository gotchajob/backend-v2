package com.example.gcj.controller;

import com.example.gcj.dto.blog.CreateBlogDTO;
import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;
import com.example.gcj.repository.CommentReactionRepository;
import com.example.gcj.service.CommentReactionService;
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
@RequestMapping("/comment-reaction")
@RequiredArgsConstructor
@Tag(name = "Comment Reaction Controller")
public class CommentReactionController {
    private final CommentReactionRepository commentReactionRepository;
    private final CommentReactionService commentReactionService;

    @PostMapping("")
//    @Secured({Role.ADMIN, Role.EXPERT, Role.USER})
//    @Operation(description = "role: admin, expert, user")
    public ResponseEntity<Response<String>> createCommentReaction(
            @RequestBody CreateCommentReactionRequestDTO request
    ) {
        try {
            commentReactionService.createCommentReaction(request);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/get-all")
//    @Secured({Role.ADMIN, Role.EXPERT, Role.USER})
//    @Operation(description = "role: admin, expert, user")
    public Response<List<CommentReactionResponseDTO>> getAllCommentReaction(
    ) {
        List<CommentReactionResponseDTO> list = commentReactionService.getAllCommentReaction();
        return Response.ok(list);
    }
}
