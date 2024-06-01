package com.example.gcj.service.impl;

import com.example.gcj.dto.comment_reaction.CommentReactionResponseDTO;
import com.example.gcj.dto.comment_reaction.CreateCommentReactionRequestDTO;
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

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;
    private final UserService userService;

    @Override
    public void createCommentReaction(CreateCommentReactionRequestDTO request) {
        User user = userService.currentUser();

        CommentReaction reaction = CommentReaction.builder()
                .commentId(request.getCommentId())
                .reactionId(request.getReactionId())
                .userId(user.getId())
                .build();

        commentReactionRepository.save(reaction);
    }

    @Override
    public List<CommentReactionResponseDTO> getAllCommentReaction() {
        List<CommentReaction> reactionList = commentReactionRepository.findAll();
        return reactionList.stream().map(CommentReactionMapper::toDto).toList();
    }

    @Override
    public List<CommentReactionResponseDTO> findReactionByCommentId(long commendId) {
        List<CommentReaction> reactionList = commentReactionRepository.findReactionByCommentId(commendId);
        if(reactionList.isEmpty()) {
            throw new CustomException("No reaction found with commentId " + commendId);
        }
        return reactionList.stream().map(CommentReactionMapper::toDto).toList();
    }
}
