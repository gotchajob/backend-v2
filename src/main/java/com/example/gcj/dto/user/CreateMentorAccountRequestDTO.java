package com.example.gcj.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateMentorAccountRequestDTO implements Serializable {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String avatar;

    private String address;

    private Date birthDate;
    private String bio;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedInUrl;
    private String education;

    private String skill;

}
