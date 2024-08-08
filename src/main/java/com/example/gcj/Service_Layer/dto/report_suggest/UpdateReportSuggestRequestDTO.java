package com.example.gcj.Service_Layer.dto.report_suggest;

import lombok.*;

@Getter
@Builder
public class UpdateReportSuggestRequestDTO {
    private String report;
    private String description;
}
