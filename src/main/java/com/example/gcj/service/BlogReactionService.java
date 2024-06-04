package com.example.gcj.service;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.dto.blog_reaction.UpdateBlogReactionDTO;

import java.util.List;

public interface BlogReactionService {
    void create(UpdateBlogReactionDTO request);
    void findBlogReactionByBlogIdAndUserId(UpdateBlogReactionDTO request);

}
