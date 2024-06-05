package com.example.gcj.dto.blog_reaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBlogReactionDTO {
    private long blogId;
    private Long reactionId;
    private Integer rating;
}
