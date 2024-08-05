package com.example.gcj.Service_Layer.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class CVListResponseDTO implements Serializable {
    private long id;
    private String image;
    private String name;
    private int status;
    private Date updatedAt;
}
