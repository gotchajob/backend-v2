package com.example.gcj.Service_Layer.dto.expert_nation_support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNationSupportRequestDTO {
    private String nation;
}
