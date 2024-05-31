package com.example.gcj.util.mapper;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.model.BlogReaction;

public class BlogReactionMapper {
    public static BlogReactionResponseDTO toDto(BlogReaction reaction) {
        return BlogReactionResponseDTO.builder()
                .id(reaction.getId())
                .userId(reaction.getUserId())
                .blogId(reaction.getBlogId())
                .reactionId(reaction.getReactionId())
                .rating(reaction.getRating())
                .build();
    }
}
