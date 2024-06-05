package com.example.gcj.service.impl;

import com.example.gcj.dto.blog_reaction.UpdateBlogReactionDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BlogReaction;
import com.example.gcj.model.Reaction;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogReactionRepository;
import com.example.gcj.repository.ReactionRepository;
import com.example.gcj.service.BlogReactionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.mapper.BlogReactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogReactionServiceImpl implements BlogReactionService {
    private final UserService userService;
    private final BlogReactionRepository blogReactionRepository;
    private final ReactionRepository reactionRepository;

    @Override
    public void create(UpdateBlogReactionDTO request) {
        User user = userService.currentUser();
        BlogReaction newReaction = new BlogReaction();
        newReaction.setUserId(user.getId());
        newReaction.setBlogId(request.getBlogId());
        newReaction.setReactionId(request.getReactionId());
        newReaction.setRating(request.getRating());
        blogReactionRepository.save(newReaction);
    }

    @Override
    public void findBlogReactionByBlogIdAndUserId(UpdateBlogReactionDTO request) {
        User user = userService.currentUser();
       BlogReaction existingReaction = blogReactionRepository.findBlogReactionByBlogIdAndUserId(request.getBlogId(), user.getId());
        if (existingReaction == null) {
            throw new CustomException("No reaction found with blogId " + user.getId());
        } else {
            BlogReaction reaction = existingReaction;
            if (request.getReactionId() != null) {
                if (request.getReactionId() == 0) {
                    reaction.setReactionId(null);
                } else {
                    reaction.setReactionId(request.getReactionId());
                }
            }
            if (request.getRating() != null) {
                reaction.setRating(request.getRating());
            }
            BlogReaction updatedReaction = blogReactionRepository.save(reaction);
            BlogReactionMapper.toDto(updatedReaction);
        }
    }

    @Override
    public void addBlogReaction(UpdateBlogReactionDTO request) {
        if (request == null) {
            throw new CustomException("request null");
        }

        if (request.getRating() != null && (request.getRating() < 1 || request.getRating() > 5)) {
            throw new CustomException("Invalid rating value");
        }

        if (request.getReactionId() != null) {
            Reaction reaction = reactionRepository.getById(request.getReactionId());
            if (reaction == null) {
                throw new CustomException("Invalid reaction id value");
            }
        }

        User user = userService.currentUser();
        if (user == null) {
            throw new CustomException("Not login yet");
        }

        BlogReaction blogReaction = blogReactionRepository.findBlogReactionByBlogIdAndUserId(request.getBlogId(), user.getId());
        if (blogReaction == null) {
            BlogReaction _blogReaction = BlogReaction
                    .builder()
                    .reactionId(request.getReactionId())
                    .blogId(request.getBlogId())
                    .userId(user.getId())
                    .rating(request.getRating())
                    .build();
            blogReactionRepository.save(_blogReaction);
            return;
        }

        if (request.getRating() != null) {
            blogReaction.setRating(request.getRating());
        }

        if (request.getReactionId() != null) {
            blogReaction.setReactionId(request.getReactionId());
        }

        if (request.getRating() == null && request.getReactionId() == null) {
            blogReaction.setReactionId(null);
        }
        blogReactionRepository.save(blogReaction);

    }


}
