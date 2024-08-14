package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import com.example.gcj.Service_Layer.dto.cv_comment.CvCommentListResponseDTO;

public class CvCommentMapper {
    public static CvCommentListResponseDTO toDto(CvShareComment cvComment) {
        if (cvComment == null) {
            return null;
        }

        return CvCommentListResponseDTO
                .builder()
                .id(cvComment.getId())
                .cvShareId(cvComment.getCvShareId())
                .customerId(cvComment.getCustomerId())
                .comment(cvComment.getComment())
                .build();
    }
}
