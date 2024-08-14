package com.example.gcj.Service_Layer.dto.cv_comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCvCommentRequestDTO {
    private long cvShareId;
    private String Comment;
}
