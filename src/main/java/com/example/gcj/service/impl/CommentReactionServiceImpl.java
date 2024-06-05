package com.example.gcj.service.impl;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;
import com.example.gcj.dto.comment_reaction.UpdateCommentReactionRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BlogComment;
import com.example.gcj.model.CommentReaction;
import com.example.gcj.model.Reaction;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogCommentRepository;
import com.example.gcj.repository.CommentReactionRepository;
import com.example.gcj.repository.ReactionRepository;
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
    private final BlogCommentRepository blogCommentRepository;
    private final ReactionRepository reactionRepository;
    private final UserService userService;


    @Override
    public void updateOrCreateCommentReaction(UpdateCommentReactionRequestDTO request) {
        if (request == null) {
            throw new CustomException("not login yet!");
        }

        BlogComment _blogComment = blogCommentRepository.getById(request.getCommentId());
        if (_blogComment == null) {
            throw new CustomException("Blog comment is not exist. id= " + request.getCommentId());
        }

        if (request.getReactionId() != null) {
            Reaction reaction = reactionRepository.getById(request.getReactionId());
            if (reaction == null) {
                throw new CustomException("Reaction is not exist. id= " + request.getReactionId());
            }
        }

        User user = userService.currentUser();
        CommentReaction existingReaction = commentReactionRepository.findByUserIdAndCommentId(user.getId(), request.getCommentId());
        if (existingReaction != null) {
            existingReaction.setReactionId(request.getReactionId());
            commentReactionRepository.save(existingReaction);
            return;
        }

        if (request.getReactionId() == null) {
            return;
        }
        CommentReaction commentReaction = CommentReaction
                .builder()
                .commentId(request.getCommentId())
                .userId(user.getId())
                .reactionId(request.getReactionId())
                .build();
        commentReactionRepository.save(commentReaction);
    }
}
