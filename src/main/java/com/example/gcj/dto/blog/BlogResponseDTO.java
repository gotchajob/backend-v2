package com.example.gcj.dto.blog;

import com.example.gcj.dto.other.LikeDTO;
import com.example.gcj.dto.user.UserProfileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class BlogResponseDTO implements Serializable {
    private long id;
    private String title;
    private String shortDescription;
    private String content;
    private String category;
    private Date createdAt;
    private long numberComment;
    private double averageRating;
    private long ratingQuantity;
    private Integer rated;
    private LikeDTO likes;
    private UserProfileDTO profile;
    private List<BlogListResponseDTO> relateBlog;
}
