package com.example.gcj.service.impl;

import com.example.gcj.dto.blog_reaction.UpdateBlogReactionDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BlogReaction;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogReactionRepository;
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
        Optional<BlogReaction> existingReaction = blogReactionRepository.findBlogReactionByBlogIdAndUserId(request.getBlogId(), user.getId()).stream().findFirst();
        if (existingReaction.isEmpty()) {
            throw new CustomException("No reaction found with blogId " + user.getId());
        } else {
            BlogReaction reaction = existingReaction.get();
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


}
