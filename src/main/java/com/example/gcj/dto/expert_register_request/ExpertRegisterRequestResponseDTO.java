package com.example.gcj.dto.expert_register_request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class ExpertRegisterRequestResponseDTO implements Serializable {
    private long id;
    private int status;
    private String email;
    private String url;
    private String note;
    private Long expertId;
    private Date createdAt;
    private Date updatedAt;
}
