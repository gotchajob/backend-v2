package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.Repository_Layer.model.BlogReaction;

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
