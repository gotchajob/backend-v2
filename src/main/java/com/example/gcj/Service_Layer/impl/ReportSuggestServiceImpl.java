package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ReportSuggest;
import com.example.gcj.Repository_Layer.repository.ReportSuggestRepository;
import com.example.gcj.Service_Layer.dto.report_suggest.CreateReportSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.UpdateReportSuggestRequestDTO;
import com.example.gcj.Service_Layer.mapper.ReportSuggestMapper;
import com.example.gcj.Service_Layer.service.ReportSuggestService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSuggestServiceImpl implements ReportSuggestService {
    private final ReportSuggestRepository reportSuggestRepository;

    @Override
    public List<ReportSuggestListResponseDTO> get() {
        List<ReportSuggest> all = reportSuggestRepository.findAll();
        return all.stream().map(ReportSuggestMapper::toDto).toList();
    }

    @Override
    public ReportSuggestResponseDTO getById(long id) {
        ReportSuggest reportSuggest = getRawById(id);

        return ReportSuggestResponseDTO
                .builder()
                .id(reportSuggest.getId())
                .report(reportSuggest.getReport())
                .description(reportSuggest.getDescription())
                .createdAt(reportSuggest.getCreatedAt())
                .build();
    }

    @Override
    public boolean create(CreateReportSuggestRequestDTO request) {
        ReportSuggest build = ReportSuggest
                .builder()
                .report(request.getReport())
                .description(request.getDescription())
                .status(1)
                .build();
        reportSuggestRepository.save(build);

        return true;
    }

    @Override
    public boolean update(long id, UpdateReportSuggestRequestDTO request) {
        ReportSuggest reportSuggest = getRawById(id);

        reportSuggest.setReport(request.getReport());
        reportSuggest.setDescription(request.getDescription());
        reportSuggestRepository.save(reportSuggest);

        return true;
    }

    @Override
    public boolean delete(long id) {
        ReportSuggest reportSuggest = getRawById(id);
        reportSuggest.setStatus(0);
        reportSuggestRepository.save(reportSuggest);

        return true;
    }

    private ReportSuggest getRawById(long id) {
        ReportSuggest reportSuggest = reportSuggestRepository.findById(id);
        if (reportSuggest == null) {
            throw new CustomException("not found report suggest with id " + id);
        }

        return  reportSuggest;
    }
}
