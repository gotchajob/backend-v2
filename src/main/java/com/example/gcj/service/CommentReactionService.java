package com.example.gcj.service;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;
import com.example.gcj.dto.comment_reaction.UpdateCommentReactionRequestDTO;

import java.util.List;

public interface CommentReactionService {
    void updateOrCreateCommentReaction(UpdateCommentReactionRequestDTO request);
}
