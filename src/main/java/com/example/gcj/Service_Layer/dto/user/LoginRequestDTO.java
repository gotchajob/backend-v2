package com.example.gcj.Service_Layer.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
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
