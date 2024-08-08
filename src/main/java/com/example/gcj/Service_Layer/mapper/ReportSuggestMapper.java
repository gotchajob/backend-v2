package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.ReportSuggest;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestListResponseDTO;

public class ReportSuggestMapper {
    public static ReportSuggestListResponseDTO toDto(ReportSuggest reportSuggest) {
        if (reportSuggest == null) {
            return null;
        }

        return ReportSuggestListResponseDTO
                .builder()
                .id(reportSuggest.getId())
                .report(reportSuggest.getReport())
                .build();
    }
}
