package com.example.gcj.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
public class SignupRequestDTO implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
