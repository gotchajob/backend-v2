package com.example.gcj.dto.blog_comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CreateBlogCommentDTO implements Serializable {
    private Long commentId;
    private String content;
}
