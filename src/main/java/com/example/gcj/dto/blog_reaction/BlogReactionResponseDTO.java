package com.example.gcj.dto.blog_reaction;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogReactionResponseDTO {
    private long id;
    private long blogId;
    private long userId;
    private Long reactionId;
    private Integer rating;
}
