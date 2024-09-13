package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.dash_board.*;
import com.example.gcj.Service_Layer.service.DashBoardService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dash-board")
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/total-money")
    @Operation(description = "finish")
    public Response<TotalMoneyTypeResponseDTO> getTotalMoney(
    ) {
        TotalMoneyTypeResponseDTO response = dashBoardService.totalMoneyType();
        return Response.ok(response);
    }

    @GetMapping("/deposit")
    @Operation(description = "finish")
    public Response<List<TransactionAmountDashBoardResponseDTO>> depositDashBoard(
            @RequestParam  int year
    ) {
        List<TransactionAmountDashBoardResponseDTO> response = dashBoardService.depositDashBoard(year);
        return Response.ok(response);
    }

    @GetMapping("/revenue")
    @Operation(description = "finish")
    public Response<List<TransactionAmountDashBoardResponseDTO>> revenueDashBoard(
            @RequestParam  int year
    ) {
        List<TransactionAmountDashBoardResponseDTO> response = dashBoardService.revenueDashBoard(year);
        return Response.ok(response);
    }

    @GetMapping("/total-booking")
    @Operation(description = "finish")
    public Response<TotalBookingResponseDTO> totalBooking(
    ) {
        TotalBookingResponseDTO response = dashBoardService.totalBooking();
        return Response.ok(response);
    }

    @GetMapping("/total-expert")
    @Operation(description = "finish")
    public Response<TotalExpertResponseDTO> totalExpert(
    ) {
        TotalExpertResponseDTO response = dashBoardService.totalExpert();
        return Response.ok(response);
    }

    @GetMapping("/expert")
    @Operation(description = "finish")
    public Response<List<ExpertDashboardResponseDTO>> expertDashboard(
    ) {
        List<ExpertDashboardResponseDTO> response = dashBoardService.expertDashboard();
        return Response.ok(response);
    }

    @GetMapping("/cv-template")
    @Operation(description = "finish")
    public Response<List<CountCvTemplateDashBoardResponseDTO>> countCvTemplate(
    ) {
        List<CountCvTemplateDashBoardResponseDTO> response = dashBoardService.countCvTemplate();
        return Response.ok(response);
    }

    @GetMapping("/cv")
    @Operation(description = "finish")
    public Response<List<CountCvUsedDashBoardResponseDTO>> countCv(
    ) {
        List<CountCvUsedDashBoardResponseDTO> response = dashBoardService.countCv();
        return Response.ok(response);
    }

    @GetMapping("/total-cv")
    @Operation(description = "finish")
    public Response<TotalCvResponseDTO> totalCv(
    ) {
        TotalCvResponseDTO response = dashBoardService.totalCv();
        return Response.ok(response);
    }

    @GetMapping("/booking")
    @Operation(description = "finish")
    public Response<List<BookingDashboardResponseDTO>> bookingDashboard(
            @RequestParam int year
    ) {
        List<BookingDashboardResponseDTO> response = dashBoardService.bookingDashboard(year);
        return Response.ok(response);
    }
    @GetMapping("/top-cv-template")
    @Operation(description = "finish")
    public Response<List<TopUseCvTemplateResponseDTO>> topCvTemplate(
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "5") @Min(1) int size
    ) {
        List<TopUseCvTemplateResponseDTO> response = dashBoardService.topCvTemplate(sort, size);
        return Response.ok(response);
    }

    @GetMapping("/top-expert")
    @Operation(description = "finish")
    public Response<List<TopExpertResponseDTO>> topExpert(
            @RequestParam(required = false, defaultValue = "5")  int size
    ) {
        List<TopExpertResponseDTO> response = dashBoardService.topExpert(size);
        return Response.ok(response);
    }







}
