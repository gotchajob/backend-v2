package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.comment_reaction.UpdateCommentReactionRequestDTO;
import com.example.gcj.Service_Layer.service.CommentReactionService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
