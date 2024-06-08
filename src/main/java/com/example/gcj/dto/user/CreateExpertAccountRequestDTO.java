package com.example.gcj.dto.user;

import com.example.gcj.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CreateExpertAccountRequestDTO implements Serializable {
    private Long expertRegisterRequestId;

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
    private int yearExperience;

    private List<String> nationSupport;
    private List<CreateExpertSkillOptionDTO> expertSKillOptionList;
}
