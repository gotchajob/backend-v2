package com.example.gcj.Service_Layer.dto.comment_reaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentReactionRequestDTO {
    private long commentId;
    private Long reactionId;
}
