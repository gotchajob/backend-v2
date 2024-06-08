package com.example.gcj.dto.user;

import com.example.gcj.dto.expert.ExpertResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserListResponseDTO implements Serializable {
    private long id;
    private String avatar;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private int roleId;
    private int status;
    private Date createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExpertResponseDTO expert;
}
