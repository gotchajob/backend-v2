package com.example.gcj.Service_Layer.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ExpertAccountResponse {
    private long userId;
    private long expertId;
    private int userStatus;
    private String email;
    private String avatar;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int yearExperience;


    private Date birthDate;
    private String bio;
    private String emailContact;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String education;
    private double cost;
    private String backgroundImage;
    private String shortDescription;
}
