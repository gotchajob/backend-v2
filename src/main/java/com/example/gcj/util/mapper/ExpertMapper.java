package com.example.gcj.util.mapper;

import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.model.Expert;
import com.example.gcj.model.User;

public class ExpertMapper {
    public static ExpertAccountResponse toDto(User user) {
        Expert expert = user.getExpert();
        if (expert == null) {
            return ExpertAccountResponse.builder().build();
        }

        return ExpertAccountResponse
                .builder()

                .userId(user.getId())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())

                .expertId(expert.getId())
                .birthDate(expert.getBirthDate())
                .bio(expert.getBio())
                .portfolioUrl(expert.getPortfolioUrl())
                .facebookUrl(expert.getFacebookUrl())
                .twitterUrl(expert.getTwitterUrl())
                .linkedinUrl(expert.getLinkedinUrl())
                .education(expert.getEducation())

                .build();
    }
}