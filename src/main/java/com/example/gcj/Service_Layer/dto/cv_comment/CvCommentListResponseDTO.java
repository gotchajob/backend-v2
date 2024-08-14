package com.example.gcj.Service_Layer.dto.cv_comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CvCommentListResponseDTO {
    private long id;
    private long cvShareId;
    private long customerId;
    private String comment;
}
