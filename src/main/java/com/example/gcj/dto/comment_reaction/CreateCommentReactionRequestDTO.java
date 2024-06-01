package com.example.gcj.dto.comment_reaction;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCommentReactionRequestDTO {
    private int commentId;
    private long userId;
    private int reactionId;
}
