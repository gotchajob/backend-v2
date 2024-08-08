package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.report_suggest.CreateReportSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.UpdateReportSuggestRequestDTO;

import java.util.List;

public interface ReportSuggestService {
    List<ReportSuggestListResponseDTO> get();

    ReportSuggestResponseDTO getById(long id);

    boolean create(CreateReportSuggestRequestDTO request);

    boolean update(long id, UpdateReportSuggestRequestDTO request);

    boolean delete(long id);
}
