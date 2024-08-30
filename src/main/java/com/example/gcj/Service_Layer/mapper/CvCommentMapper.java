package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.CvShareComment;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareCommentListResponseDTO;

public class CvCommentMapper {
    public static CvShareCommentListResponseDTO toDto(CvShareComment cvComment) {
        if (cvComment == null) {
            return null;
        }

        return CvShareCommentListResponseDTO
                .builder()
                .id(cvComment.getId())
                .cvShareId(cvComment.getCvShareId())
                .customerId(cvComment.getCustomerId())
                .comment(cvComment.getComment())
                .rating(cvComment.getRating())
                .build();
    }
}
