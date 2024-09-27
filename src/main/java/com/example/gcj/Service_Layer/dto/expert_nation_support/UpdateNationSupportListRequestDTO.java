package com.example.gcj.Service_Layer.dto.expert_nation_support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateNationSupportListRequestDTO {
    private List<String> nations;
}
