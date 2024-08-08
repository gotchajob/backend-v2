package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Expert extends AbstractEntity {
    private int status;
    private Date birthDate;
    private String emailContact;
    private String bio;
    private String portfolioUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String education;
    private int yearExperience;
    private int personalPoint;
    private double cost;
    private String certification;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
