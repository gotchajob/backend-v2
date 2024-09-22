package com.example.gcj.Service_Layer.dto.staff;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateStaffRequestDTO {
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
