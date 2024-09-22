package com.example.gcj.Service_Layer.dto.user;

import com.example.gcj.Shared.util.Regex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO implements Serializable {
    @Schema(example = "user@gmail.com")
    @Pattern(regexp = Regex.EMAIL, message = "invalid email pattern")
    private String email;
    @Schema(example = "Test12345")
    @Pattern(regexp = Regex.PASSWORD, message = "invalid password pattern")
    private String password;
}
