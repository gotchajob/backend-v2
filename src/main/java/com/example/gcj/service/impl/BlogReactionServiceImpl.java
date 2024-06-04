package com.example.gcj.service.impl;

import com.example.gcj.dto.blog_reaction.BlogReactionResponseDTO;
import com.example.gcj.dto.blog_reaction.CreateBlogReactionRequestDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogReactionServiceImpl implements BlogReactionService {
    private final UserService userService;
    private final BlogReactionRepository blogReactionRepository;

    @Override
    public void create(CreateBlogReactionRequestDTO request, long blogId) {
        User user = userService.currentUser();

        //todo: check user is null?
        //todo: check user liked, if liked throw CustomerException
        //todo: check request. blog is exist?, reactionId is exist?

        BlogReaction reaction = BlogReaction.builder()
                .blogId(blogId)
                .reactionId(request.getReactionId())
                .userId(user.getId())
                .rating(request.getRating())
                .build();

        blogReactionRepository.save(reaction);
    }

    @Override
    public List<BlogReactionResponseDTO> getAll() {
        List<BlogReaction> reactionList = blogReactionRepository.findAll();
        return reactionList.stream().map(BlogReactionMapper::toDto).toList();
    }

    @Override
    public List<BlogReactionResponseDTO> findReactionByBlogId(long blogId) {
        List<BlogReaction> reactList = blogReactionRepository.findReactionByBlogId(blogId);
        if (reactList.isEmpty()) {
            throw new CustomException("No reaction found with blogId " + blogId);
        }
        return reactList.stream().map(BlogReactionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BlogReactionResponseDTO> findReactionByUserId(int userid) {
        List<BlogReaction> reactList = blogReactionRepository.findReactionByUserId(userid);
        if (reactList.isEmpty()) {
            throw new CustomException("No reaction found with blogId " + userid);
        }
        return reactList.stream().map(BlogReactionMapper::toDto).collect(Collectors.toList());
    }

        @Override
        public List<BlogReactionResponseDTO> findReactionByBlogIdAndUserId(long blogId, long userId) {
            List<BlogReaction> reactList = blogReactionRepository.findReactionByBlogIdAndUserId(blogId, userId);
            if (reactList.isEmpty()) {
                throw new CustomException("No reaction found with blogId " + userId);
            }
            return reactList.stream().map(BlogReactionMapper::toDto).collect(Collectors.toList());
        }


    @Override
    public void updateBlogReaction(UpdateBlogReactionDTO request, long id) {
        Optional<BlogReaction> optionalReaction = blogReactionRepository.findById(id);
        if (optionalReaction.isEmpty()) {
            throw new CustomException("BlogReaction not found");
        }
        BlogReaction reaction = optionalReaction.get();
        reaction.setBlogId(request.getBlogId());
        reaction.setUserId(request.getUserId());
        reaction.setReactionId(request.getReactionId());
        reaction.setRating(request.getRating());

        blogReactionRepository.save(reaction);
    }

    @Override
    public void delteBlogReaction(long id) {
        if(!blogReactionRepository.existsById(id)) {
            throw new CustomException("Blog reaction not found with id" + id);
        }
        blogReactionRepository.deleteById(id);
    }


}
