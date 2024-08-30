package com.example.gcj.Service_Layer.dto.cv_share;

import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class CvShareResponseDTO {
    private long id;
    private String caption;
    private String cvImage;
    private long categoryId;
    private String category;
    private Date createdAt;

    private List<CvShareRatingListResponseDTO> rating;

    private UserInfoResponseDTO userInfo;
}
