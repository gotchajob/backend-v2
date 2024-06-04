package com.example.gcj.service.impl;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;
import com.example.gcj.dto.comment_reaction.UpdateCommentReactionRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.CommentReaction;
import com.example.gcj.model.User;
import com.example.gcj.repository.CommentReactionRepository;
import com.example.gcj.service.CommentReactionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.mapper.BlogReactionMapper;
import com.example.gcj.util.mapper.CommentReactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;
    private final UserService userService;

    @Override
    public void updateOrCreateCommentReaction(UpdateCommentReactionRequestDTO request) {
        User user = userService.currentUser();
        Optional<CommentReaction> existingReaction = commentReactionRepository.findByUserIdAndCommentId(user.getId(), request.getCommentId());
        if (existingReaction.isPresent()) {
            // Update existing reaction
            CommentReaction reaction = existingReaction.get();
            reaction.setReactionId(request.getReactionId());
            commentReactionRepository.save(reaction);
        } else {
            // Create new reaction
            CommentReaction newReaction = new CommentReaction();
            newReaction.setUserId(user.getId());
            newReaction.setCommentId(request.getCommentId());
            newReaction.setReactionId(request.getReactionId());
            commentReactionRepository.save(newReaction);
        }
    }
}
