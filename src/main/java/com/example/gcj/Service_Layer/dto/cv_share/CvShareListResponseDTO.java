package com.example.gcj.Service_Layer.dto.cv_share;

import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvShareListResponseDTO {
    private long id;
    private long customerId;
    private String caption;
    private String cvImage;
    private long categoryId;
    private String category;
    private int status;
    private Date createdAt;

    private List<CvShareRatingListResponseDTO> rating;

    private UserInfoResponseDTO userInfo;
}
