package com.example.gcj.Service_Layer.dto.blog_comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CreateBlogCommentDTO implements Serializable {
    private Long commentId;
    @NotBlank
    private String content;
}
