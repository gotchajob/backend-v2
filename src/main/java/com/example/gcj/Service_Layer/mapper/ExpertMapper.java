package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert.ExpertResponseDTO;
import com.example.gcj.Service_Layer.dto.user.ExpertAccountResponse;
import com.example.gcj.Repository_Layer.model.Expert;
import com.example.gcj.Repository_Layer.model.User;

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
                .emailContact(expert.getEmailContact())
                .birthDate(expert.getBirthDate())
                .bio(expert.getBio())
                .portfolioUrl(expert.getPortfolioUrl())
                .facebookUrl(expert.getFacebookUrl())
                .twitterUrl(expert.getTwitterUrl())
                .linkedinUrl(expert.getLinkedinUrl())
                .yearExperience(expert.getYearExperience())
                .education(expert.getEducation())
                .cost(expert.getCost())
                .shortDescription(expert.getShortDescription())
                .backgroundImage(expert.getBackgroundImage())

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
                .expertId(expert.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .yearExperience(expert.getYearExperience())
                .email(user.getEmail())
                .bio(expert.getBio())
                .avatar(user.getAvatar())
                .point(point)
                .cost(expert.getCost())
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
                .emailContact(expert.getEmailContact())
                .portfolioUrl(expert.getPortfolioUrl())
                .facebookUrl(expert.getFacebookUrl())
                .twitterUrl(expert.getTwitterUrl())
                .linkedinUrl(expert.getLinkedinUrl())
                .education(expert.getEducation())
                .yearExperience(expert.getYearExperience())
                .cost(expert.getCost())
                .shortDescription(expert.getShortDescription())
                .backgroundImage(expert.getBackgroundImage())

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
                .emailContact(expert.getEmailContact())
                .portfolioUrl(expert.getPortfolioUrl())
                .facebookUrl(expert.getFacebookUrl())
                .twitterUrl(expert.getTwitterUrl())
                .linkedinUrl(expert.getLinkedinUrl())
                .education(expert.getEducation())
                .yearExperience(expert.getYearExperience())
                .personalPoint(expert.getPersonalPoint())
                .cost(expert.getCost())
                .shortDescription(expert.getShortDescription())
                .backgroundImage(expert.getBackgroundImage())
                .build();
    }
}
