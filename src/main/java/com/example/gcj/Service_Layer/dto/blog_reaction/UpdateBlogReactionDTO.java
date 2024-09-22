package com.example.gcj.Service_Layer.dto.blog_reaction;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBlogReactionDTO {
    @Min(1)
    private long blogId;
    @Min(1)
    private Long reactionId;
    @Min(1)
    @Max(5)
    private Integer rating;
}
