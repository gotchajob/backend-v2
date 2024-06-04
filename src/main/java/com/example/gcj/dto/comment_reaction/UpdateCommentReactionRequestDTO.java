package com.example.gcj.dto.comment_reaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentReactionRequestDTO {
    private int commentId;
    private int reactionId;
}
