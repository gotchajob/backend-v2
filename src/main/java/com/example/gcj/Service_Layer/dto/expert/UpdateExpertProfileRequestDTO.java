package com.example.gcj.Service_Layer.dto.expert;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UpdateExpertProfileRequestDTO {
    private String emailContact;
    private Date birthDate;
    private String bio;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedInUrl;
    private String education;
    private int yearExperience;
    private String backgroundImage;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
}
