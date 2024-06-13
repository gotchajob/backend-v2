package com.example.gcj.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserProfileDTO implements Serializable {
    private long id;
    private String avatar;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private int roleId;
}
