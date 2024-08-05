package com.example.gcj.Service_Layer.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class LoginResponseDTO implements Serializable {
    private String token;
    private int roleId;
}
