package com.example.gcj.Service_Layer.dto.comment_reaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentReactionResponseDTO {
    private long id;
    private long userId;
    private long commentId;
    private Long reactionId;
}
