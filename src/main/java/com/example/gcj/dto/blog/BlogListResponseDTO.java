package com.example.gcj.dto.blog;

import com.example.gcj.dto.user.UserProfileDTO;
import com.example.gcj.model.Blog;
import lombok.Builder;
import lombok.Data;
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
    private Date createdAt;
    private UserProfileDTO profile;
}
