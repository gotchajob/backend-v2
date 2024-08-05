package com.example.gcj.Service_Layer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserBookingInfoResponseDTO {
    private String fullName;
    private String email;
    private String avatar;

    public UserBookingInfoResponseDTO(String firstName, String lastName, String email, String avatar) {
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.avatar = avatar;
    }
}
