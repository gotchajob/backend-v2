package com.example.gcj.Service_Layer.dto.blog_comment;

import com.example.gcj.Service_Layer.dto.other.LikeDTO;
import com.example.gcj.Service_Layer.dto.user.UserProfileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class BlogCommentListDTO implements Serializable {
    private long id;
    private String content;
    private Date createdAt;

    private long reply;
    private LikeDTO likes;
    private UserProfileDTO profile;
}
