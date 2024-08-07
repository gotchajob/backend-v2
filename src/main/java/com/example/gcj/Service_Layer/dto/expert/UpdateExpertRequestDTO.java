package com.example.gcj.Service_Layer.dto.expert;

import com.example.gcj.Service_Layer.dto.expert_skill_option.CreateExpertSkillOptionDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class UpdateExpertRequestDTO {
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
