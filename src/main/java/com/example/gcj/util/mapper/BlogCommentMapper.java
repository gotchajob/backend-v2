package com.example.gcj.util.mapper;

import com.example.gcj.dto.blog_comment.BlogCommentListDTO;
import com.example.gcj.model.BlogComment;

public class BlogCommentMapper {
    public static BlogCommentListDTO toDto(BlogComment comment) {
        return BlogCommentListDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .reply(comment.getReplies().size())
                .profile(UserMapper.toUserProfile(comment.getUser()))
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
