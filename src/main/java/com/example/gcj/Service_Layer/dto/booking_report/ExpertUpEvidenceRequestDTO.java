package com.example.gcj.Service_Layer.dto.booking_report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpertUpEvidenceRequestDTO {
    private String content;
    private String evidence;
}
