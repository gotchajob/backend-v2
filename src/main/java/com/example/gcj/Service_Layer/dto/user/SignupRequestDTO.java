package com.example.gcj.Service_Layer.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Builder
public class SignupRequestDTO implements Serializable {
    private String email;

    @Length(min = 8)
    private String password;
    @NotBlank
    @Length(min = 2)
    private String firstName;
    @NotBlank
    @Length(min = 2)
    private String lastName;
}
