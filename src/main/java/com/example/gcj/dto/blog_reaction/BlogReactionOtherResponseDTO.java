package com.example.gcj.dto.blog_reaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogReactionOtherResponseDTO {
    private double averageRating;
    private int ratingQuantity;
}
