package com.example.gcj.util.mapper;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.model.CommentReaction;

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
