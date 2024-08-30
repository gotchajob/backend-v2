package com.example.gcj.Service_Layer.dto.cv_share_comment;

import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvShareCommentListResponseDTO {
    private long id;
    private long cvShareId;
    private long customerId;
    private String comment;
    private int rating;

    private UserInfoResponseDTO useInfo;
}
