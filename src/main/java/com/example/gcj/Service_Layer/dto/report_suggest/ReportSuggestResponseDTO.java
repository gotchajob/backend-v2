package com.example.gcj.Service_Layer.dto.report_suggest;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ReportSuggestResponseDTO {
    private long id;
    private String report;
    private String description;
    private Date createdAt;
}
