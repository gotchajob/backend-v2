package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.blog_reaction.UpdateBlogReactionDTO;

public interface BlogReactionService {
    void create(UpdateBlogReactionDTO request);
    void findBlogReactionByBlogIdAndUserId(UpdateBlogReactionDTO request);
    void addBlogReaction(UpdateBlogReactionDTO request);

}
