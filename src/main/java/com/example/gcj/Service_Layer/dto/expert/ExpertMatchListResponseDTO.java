package com.example.gcj.Service_Layer.dto.expert;

import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExpertMatchListResponseDTO implements Serializable {
    private long userId;
    private long expertId;
    private String fullName;
    private String avatar;
    private String email;
    private int yearExperience;
    private String bio;
    private double cost;
    private List<ExpertSkillOptionResponseDTO> Skills;
    private List<ExpertNationSupportResponseDTO> nationSupport;

    private double point;


    public ExpertMatchListResponseDTO(long userId, long expertId, String firstName, String lastName, String avatar, String email, int yearExperience, String bio, double cost, double point) {
        this.userId = userId;
        this.expertId = expertId;
        this.fullName = firstName + " " + lastName;
        this.avatar = avatar;
        this.email = email;
        this.yearExperience = yearExperience;
        this.bio = bio;
        this.cost = cost;
        this.point = point;
    }
}
