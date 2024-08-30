package com.example.gcj.Service_Layer.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserProfileRequestDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
}
