package com.example.gcj.dto.user;

import com.example.gcj.util.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO implements Serializable {
    @Schema(example = "user@gmail.com")
    private String email;
    @Schema(example = "Test12345")
    private String password;
}
