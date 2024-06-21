package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class CvResponseDTO implements Serializable {
    private long id;
    private long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String name;
    private String cv;
    private int status;
    private Date createdAt;
    private Date updatedAt;
}
