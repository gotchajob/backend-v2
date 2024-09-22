package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.blog_reaction.UpdateBlogReactionDTO;
import com.example.gcj.Service_Layer.service.BlogReactionService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/blog-reaction")
@RequiredArgsConstructor
@Tag(name = "Blog Reaction Controller")
public class BlogReactionController {
    private final BlogReactionService blogReactionService;

    @PatchMapping("")
    @Secured({Role.USER, Role.EXPERT})
    public Response<String> updateBlogReaction(
            @RequestBody @Valid UpdateBlogReactionDTO request
    ) {
        blogReactionService.addBlogReaction(request);
        return Response.ok(null);
    }

}
