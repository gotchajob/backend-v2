package com.example.gcj.dto.skill_option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SkillOptionBookingResponseDTO {
    private long skillId;
    private String skillName;
    private long skillOptionId;
    private String skillOptionName;
}
