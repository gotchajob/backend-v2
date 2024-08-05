package com.example.gcj.Service_Layer.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SignupRequestDTO implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
