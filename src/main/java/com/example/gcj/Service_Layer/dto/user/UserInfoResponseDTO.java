package com.example.gcj.Service_Layer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponseDTO {
    private long userId;
    private String fullName;
    private String email;
    private String avatar;

    public UserInfoResponseDTO(long userId, String firstName, String lastName, String email, String avatar) {
        this.userId = userId;
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.avatar = avatar;
    }
}
