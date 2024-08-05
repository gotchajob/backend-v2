package com.example.gcj.Service_Layer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginDataResponseDTO {
    private long id;
    private String email;
    private String password;
    private int status;
    private int roleId;
}
