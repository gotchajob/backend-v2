package com.example.gcj.Service_Layer.dto.cv_share_comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCvShareCommentRequestDTO {
    @Min(1)
    private long cvShareId;
    @NotBlank
    private String comment;
    @Min(1)
    @Max(5)
    private int rating;
}
