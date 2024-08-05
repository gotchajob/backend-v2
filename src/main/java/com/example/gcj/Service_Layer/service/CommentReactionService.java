package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.comment_reaction.UpdateCommentReactionRequestDTO;

public interface CommentReactionService {
    void updateOrCreateCommentReaction(UpdateCommentReactionRequestDTO request);
}
