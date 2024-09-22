package com.example.gcj.Service_Layer.dto.skill;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSkillRequestDTO {
    @Min(1)
    private long categoryId;
    @NotBlank
    private String name;
}
