package com.example.gcj.service.impl;

import com.example.gcj.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.dto.blog_comment.CommentWithReplyCountDTO;
import com.example.gcj.dto.blog_comment.CreateBlogCommentDTO;
import com.example.gcj.dto.other.LikeDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.model.Blog;
import com.example.gcj.model.BlogComment;
import com.example.gcj.model.User;
import com.example.gcj.repository.BlogCommentRepository;
import com.example.gcj.repository.BlogReactionRepository;
import com.example.gcj.repository.CommentReactionRepository;
import com.example.gcj.service.BlogCommentService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.mapper.BlogCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {
    private final static int ACTIVE_STATUS = 1;

    private final BlogCommentRepository blogCommentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final UserService userService;


    @Override
    public void create(CreateBlogCommentDTO request, long blogId) {
        User user = userService.currentUser();

        BlogComment blogComment = BlogComment.builder()
                .blog(new Blog(blogId))
                .parentComment(request.getCommentId() == null ? null : new BlogComment(request.getCommentId()))
                .user(user)
                .content(request.getContent())
                .status(ACTIVE_STATUS)
                .build();

        blogCommentRepository.save(blogComment);
    }

    @Override
    public PageResponseDTO<BlogCommentListDTO> get(Long blogId, Long parentCommentId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<BlogComment> comments = blogCommentRepository.getByBlogIdAndParentCommentId(blogId, parentCommentId, pageable);

        User user = userService.currentUser();
        List<BlogCommentListDTO> blogCommentList = comments.map(BlogCommentMapper::toDto).toList();
        for (BlogCommentListDTO i : blogCommentList) {
            long value = commentReactionRepository.countByCommentIdAndReactionIdNotNull(i.getId());
            boolean liked = false;
            if (user != null) {
                liked = commentReactionRepository.existsByCommentIdAndUserIdAndReactionIdNotNull(i.getId(), user.getId());
            }
            i.setLikes(LikeDTO.builder().liked(liked).value(value).build());
        }
        return new PageResponseDTO<>(blogCommentList, comments.getTotalPages());
    }
}
