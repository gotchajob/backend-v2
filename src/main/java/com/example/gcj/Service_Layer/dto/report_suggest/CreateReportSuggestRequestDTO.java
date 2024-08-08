package com.example.gcj.Service_Layer.dto.report_suggest;

import lombok.*;

@Getter
@Builder
public class CreateReportSuggestRequestDTO {
    private String report;
    private String description;
}
