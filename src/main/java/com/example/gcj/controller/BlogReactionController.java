package com.example.gcj.controller;

import com.example.gcj.dto.blog_reaction.UpdateBlogReactionDTO;
import com.example.gcj.repository.BlogReactionRepository;
import com.example.gcj.service.BlogReactionService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/blog-reaction")
@RequiredArgsConstructor
@Tag(name = "Blog Reaction Controller")
public class BlogReactionController {
    private static final Logger log = LoggerFactory.getLogger(BlogReactionController.class);
    private final BlogReactionService blogReactionService;
    private final BlogReactionRepository blogReactionRepository;

    @PatchMapping("")
    @Secured({Role.USER, Role.EXPERT})
    public ResponseEntity<Response<String>> updateBlogReaction(
            @RequestBody UpdateBlogReactionDTO request
    ) {
        boolean exist = blogReactionRepository.existsByBlogIdAndUserId(request.getBlogId(), request.getUserId());
        log.info("Value of exist: {}", exist);
        if(!exist) {
            blogReactionService.create(request);
        } else {
            blogReactionService.findBlogReactionByBlogIdAndUserId(request);
        }
        return Response.success(null);
    }

}
