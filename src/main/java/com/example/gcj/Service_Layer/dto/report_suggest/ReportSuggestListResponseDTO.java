package com.example.gcj.Service_Layer.dto.report_suggest;

import lombok.*;

@Getter
@Builder
public class ReportSuggestListResponseDTO {
    private long id;
    private String report;
    private String description;
}
