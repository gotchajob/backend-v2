package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.dash_board.*;

import java.util.List;

public interface DashBoardService {
    TotalMoneyTypeResponseDTO totalMoneyType();
    TotalBookingResponseDTO totalBooking();
    TotalExpertResponseDTO totalExpert();

    List<TransactionAmountDashBoardResponseDTO> depositDashBoard(int year);

    List<TransactionAmountDashBoardResponseDTO> revenueDashBoard(int year);
    List<CountCvTemplateDashBoardResponseDTO> countCvTemplate();

    List<CountCvUsedDashBoardResponseDTO> countCv();

    TotalCvResponseDTO totalCv();

    List<TopExpertResponseDTO> topExpert(int size);

    List<TopUseCvTemplateResponseDTO> topCvTemplate(String sort, int size);

    List<BookingDashboardResponseDTO> bookingDashboard(int year);

    List<ExpertDashboardResponseDTO> expertDashboard();
}
