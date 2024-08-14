package com.example.gcj.Service_Layer.dto.cv_share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCvShareRequestDTO {
    private String caption;
}
