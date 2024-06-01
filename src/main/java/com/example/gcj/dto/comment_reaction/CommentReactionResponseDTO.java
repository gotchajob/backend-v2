package com.example.gcj.dto.comment_reaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentReactionResponseDTO {
    private long id;
    private long userId;
    private int commentId;
    private int reactionId;
}
