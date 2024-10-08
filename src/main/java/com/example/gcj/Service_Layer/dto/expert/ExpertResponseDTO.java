package com.example.gcj.Service_Layer.dto.expert;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ExpertResponseDTO {
    private long expertId;
    private int status;
    private Date birthDate;
    private String bio;
    private String emailContact;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String education;
    private int yearExperience;
    private int personalPoint;
    private double cost;
    private String backgroundImage;
    private String shortDescription;
    private String certificate;
}
