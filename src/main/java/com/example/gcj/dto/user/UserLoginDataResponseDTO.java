package com.example.gcj.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginDataResponseDTO {
    private String email;
    private String password;
    private int status;
    private int roleId;
}
