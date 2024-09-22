package com.example.gcj.Service_Layer.dto.booking_report;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpertUpEvidenceRequestDTO {
    @NotBlank
    private String content;
    private String evidence;
}
