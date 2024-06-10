package com.example.gcj.util.mapper;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert.ExpertResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Expert;
import com.example.gcj.model.User;
import org.springframework.util.StringUtils;

public class ExpertMapper {
    public static ExpertAccountResponse toDto(User user) {
        Expert expert = user.getExpert();
        if (expert == null) {
            return null;
        }

        return ExpertAccountResponse
                .builder()

                .userId(user.getId())
                .email(user.getEmail())
                .userStatus(user.getStatus())
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

    public static ExpertMatchListResponseDTO toDto(Expert expert, double point) {
        User user = expert.getUser();
        if (user == null) {
            return null;
        }
        return ExpertMatchListResponseDTO
                .builder()
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .yearExperience(expert.getYearExperience())
                .email(user.getEmail())
                .bio(expert.getBio())
                .avatar(user.getAvatar())
                .point(point)
                .build();
    }

    public static ExpertAccountResponse toDto(Expert expert) {
        User user = expert.getUser();
        if (user == null) {
            return null;
        }

        return ExpertAccountResponse
                .builder()

                .userId(user.getId())
                .email(user.getEmail())
                .userStatus(user.getStatus())
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
                .yearExperience(expert.getYearExperience())

                .build();
    }

    public static ExpertResponseDTO toDtoSimple(Expert expert) {
        if (expert == null) {
            return null;
        }

        return ExpertResponseDTO
                .builder()
                .expertId(expert.getId())
                .status(expert.getStatus())
                .birthDate(expert.getBirthDate())
                .bio(expert.getBio())
                .portfolioUrl(expert.getPortfolioUrl())
                .facebookUrl(expert.getFacebookUrl())
                .twitterUrl(expert.getTwitterUrl())
                .linkedinUrl(expert.getLinkedinUrl())
                .education(expert.getEducation())
                .yearExperience(expert.getYearExperience())
                .build();
    }
}
