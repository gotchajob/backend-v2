package com.example.gcj.Service_Layer.dto.blog_reaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateBlogReactionRequestDTO {
    private long userId;
    private long blogId;
    private int reactionId;
    private int rating;
}
