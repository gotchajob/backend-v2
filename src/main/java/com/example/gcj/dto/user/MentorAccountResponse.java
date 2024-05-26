package com.example.gcj.dto.user;

import com.example.gcj.model.Expert;
import com.example.gcj.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class MentorAccountResponse {
    private long id;
    private String email;
    private String avatar;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;


    private Date birthDate;
    private String bio;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String education;
    private String skill;
    private String skillDescription;

    public MentorAccountResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.address = user.getAddress();

        Expert expert = user.getExpert();
        if (expert != null) {
            this.birthDate = expert.getBirthDate();
            this.bio = expert.getBio();
            this.portfolioUrl = expert.getPortfolioUrl();
            this.facebookUrl = expert.getFacebookUrl();
            this.twitterUrl = expert.getTwitterUrl();
            this.linkedinUrl = expert.getLinkedinUrl();
            this.education = expert.getEducation();
        }
    }
}
