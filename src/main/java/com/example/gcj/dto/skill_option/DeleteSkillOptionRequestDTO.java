package com.example.gcj.dto.skill_option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteSkillOptionRequestDTO {
    private List<Long> skillIds;
}
