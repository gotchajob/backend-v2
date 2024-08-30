package com.example.gcj.Service_Layer.dto.cv_share_comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CvShareRatingListResponseDTO {
    private int rating;
    private long count;
}
