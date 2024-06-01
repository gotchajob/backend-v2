package com.example.gcj.service;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;

import java.util.List;

public interface CommentReactionService {
    void createCommentReaction(CreateCommentReactionRequestDTO request);
    List<CommentReactionResponseDTO> getAllCommentReaction();
    List<CommentReactionResponseDTO> findReactionByCommentId(long commendId);
}
