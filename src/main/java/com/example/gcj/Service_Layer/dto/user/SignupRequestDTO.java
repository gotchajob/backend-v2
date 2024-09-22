package com.example.gcj.Service_Layer.dto.user;

import com.example.gcj.Shared.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Builder
public class SignupRequestDTO implements Serializable {
    @Pattern(regexp = Regex.EMAIL, message = "invalid email pattern")
    private String email;
    @Pattern(regexp = Regex.PASSWORD, message = "invalid password pattern")
    private String password;
    @NotBlank
    @Length(min = 2)
    private String firstName;
    @NotBlank
    @Length(min = 2)
    private String lastName;
}
