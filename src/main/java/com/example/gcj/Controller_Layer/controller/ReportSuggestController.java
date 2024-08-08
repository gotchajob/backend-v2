package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.report_suggest.CreateReportSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.ReportSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.report_suggest.UpdateReportSuggestRequestDTO;
import com.example.gcj.Service_Layer.service.ReportSuggestService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report-suggest")
@RequiredArgsConstructor
public class ReportSuggestController {
    private final ReportSuggestService reportSuggestService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<ReportSuggestListResponseDTO>> get(
    ) {
        List<ReportSuggestListResponseDTO> response = reportSuggestService.get();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<ReportSuggestResponseDTO> getById(
            @PathVariable long id
    ) {
        ReportSuggestResponseDTO response = reportSuggestService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateReportSuggestRequestDTO request
    ) {
        reportSuggestService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateReportSuggestRequestDTO request
    ) {
        reportSuggestService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        reportSuggestService.delete(id);
        return Response.ok(null);
    }
}
