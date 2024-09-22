package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.User;
import com.example.gcj.Service_Layer.dto.staff.StaffListResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserListResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserProfileDTO;

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
                .expert(ExpertMapper.toDtoSimple(user.getExpert()))
                .build();
    }

    public static StaffListResponseDTO staffDTO(User user) {
        if (user == null) {
            return StaffListResponseDTO.builder().build();
        }

        return StaffListResponseDTO.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
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
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .roleId(user.getRoleId())
                .build();
    }
}
