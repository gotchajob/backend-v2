package com.example.gcj.dto.user;

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
    private int status;
    private Date createdAt;
}
