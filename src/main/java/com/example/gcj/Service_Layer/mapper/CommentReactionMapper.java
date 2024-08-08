package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.Repository_Layer.model.CommentReaction;

public class CommentReactionMapper {
    public static CommentReactionResponseDTO toDto(CommentReaction reaction) {
        return CommentReactionResponseDTO.builder()
                .id(reaction.getId())
                .commentId(reaction.getCommentId())
                .reactionId(reaction.getReactionId())
                .userId(reaction.getUserId())
                .build();
    }
}
