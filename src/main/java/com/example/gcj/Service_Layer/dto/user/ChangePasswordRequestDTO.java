package com.example.gcj.Service_Layer.dto.user;

import com.example.gcj.Shared.util.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ChangePasswordRequestDTO implements Serializable {
    @NotBlank(message = "old password {validation.NotBlank.message}")
    private String oldPassword;

    @NotBlank(message = "new password {validation.NotBlank.message}")
    @Size(min = 8, max = 256, message = "new password {validation.size.message}")
    @Pattern(regexp = Regex.PASSWORD, message = "new password is {validation.pattern.message}")
    private String newPassword;
}
