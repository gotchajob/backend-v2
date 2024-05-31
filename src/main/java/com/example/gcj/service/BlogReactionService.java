package com.example.gcj.service;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.dto.blog_reaction.CreateBlogReactionRequestDTO;

import java.util.List;

public interface BlogReactionService {
    void create(CreateBlogReactionRequestDTO request, long blogId);
    List<BlogReactionResponseDTO> getAll();
    List<BlogReactionResponseDTO> findReactionByBlogId(long blogId);
    List<BlogReactionResponseDTO> findReactionByUserId(int userid);
}
