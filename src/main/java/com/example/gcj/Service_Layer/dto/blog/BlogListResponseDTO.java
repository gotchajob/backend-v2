package com.example.gcj.Service_Layer.dto.blog;

import com.example.gcj.Service_Layer.dto.user.UserProfileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class BlogListResponseDTO implements Serializable {
    private long id;
    private String title;
    private String thumbnail;
    private String shortDescription;
    private long categoryId;
    private String category;
    private Date createdAt;
    private UserProfileDTO profile;
}
