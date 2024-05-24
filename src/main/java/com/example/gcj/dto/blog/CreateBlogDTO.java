package com.example.gcj.dto.blog;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateBlogDTO implements Serializable {
    private String thumbnail;
    private String title;
    private String shortDescription;
    private String content;
    private int categoryId;
}
