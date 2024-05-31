package com.example.gcj.repository;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.model.BlogReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogReactionRepository extends JpaRepository<BlogReaction, Long> {
    List<BlogReaction> findAll();
    List<BlogReaction> findReactionByBlogId(long blogId);
    List<BlogReaction> findReactionByUserId(int userId);

}
