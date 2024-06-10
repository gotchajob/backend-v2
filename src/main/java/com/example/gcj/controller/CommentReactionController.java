package com.example.gcj.controller;

import com.example.gcj.dto.comment_reaction.UpdateCommentReactionRequestDTO;
import com.example.gcj.service.CommentReactionService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-reaction")
@RequiredArgsConstructor
@Tag(name = "Comment Reaction Controller")
public class CommentReactionController {
    private final CommentReactionService commentReactionService;

    @PatchMapping
    @Secured({Role.USER})
    public Response<String> updateOrCreateCommentReaction(@RequestBody UpdateCommentReactionRequestDTO request) {
        commentReactionService.updateOrCreateCommentReaction(request);
        return Response.ok(null);
    }
}
