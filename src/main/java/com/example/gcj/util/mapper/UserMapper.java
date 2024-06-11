package com.example.gcj.util.mapper;

import com.example.gcj.dto.user.UserListResponseDTO;
import com.example.gcj.dto.user.UserProfileDTO;
import com.example.gcj.model.User;

public class UserMapper {
    public static UserListResponseDTO toDto(User user) {
        if (user == null) {
            return UserListResponseDTO.builder().build();
        }

        return UserListResponseDTO.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .status(user.getStatus())
                .roleId(user.getRoleId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserProfileDTO toUserProfile(User user) {
        if (user == null) {
            return UserProfileDTO.builder().build();
        }

        return UserProfileDTO.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .avatar(user.getAvatar())
                .build();
    }
}
