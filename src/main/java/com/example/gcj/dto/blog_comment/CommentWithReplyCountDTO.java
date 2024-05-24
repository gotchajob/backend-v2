package com.example.gcj.dto.blog_comment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentWithReplyCountDTO {
    private Long commentId;
    private String content;
    private int status;
    private Long userId;
    private Long parentCommentId;
    private Long blogId;
    private Long replyCount;
}
