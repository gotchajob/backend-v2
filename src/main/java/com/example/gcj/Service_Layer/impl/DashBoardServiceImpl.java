package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.repository.*;
import com.example.gcj.Service_Layer.dto.dash_board.*;
import com.example.gcj.Service_Layer.service.DashBoardService;
import com.example.gcj.Shared.util.status.BookingStatus;
import com.example.gcj.Shared.util.status.ExpertRegisterRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final RevenueRepository revenueRepository;
    private final TransactionRepository transactionRepository;
    private final BookingRepository bookingRepository;
    private final ExpertRegisterRequestRepository expertRegisterRequestRepository;
    private final CvTemplateRepository cvTemplateRepository;
    private final CvRepository cvRepository;
    private final ExpertSkillOptionRepository expertSkillOptionRepository;

    @Override
    public TotalMoneyTypeResponseDTO totalMoneyType() {
        double totalRevenue = Optional.ofNullable(revenueRepository.totalRevenue()).orElse(0.0);
        double totalDeposit = Optional.ofNullable(transactionRepository.totalDeposit()).orElse(0.0);
        double totalWithDraw = Optional.ofNullable(transactionRepository.totalWithDraw()).orElse(0.0);

        return TotalMoneyTypeResponseDTO
                .builder()
                .totalRevenue(totalRevenue)
                .totalDeposit(totalDeposit)
                .totalWithDraw(totalWithDraw)
                .build();
    }

    @Override
    public TotalBookingResponseDTO totalBooking() {
        long totalBooking = bookingRepository.count();
        long totalCompleteBooking = bookingRepository.countByStatus(BookingStatus.COMPLETE);
        double totalPayForService = Optional.ofNullable(transactionRepository.totalPayForService()).orElse(0.0);

        return TotalBookingResponseDTO
                .builder()
                .totalBooking(totalBooking)
                .totalCompleteBooking(totalCompleteBooking)
                .totalPayForService(totalPayForService)
                .build();
    }

    @Override
    public TotalExpertResponseDTO totalExpert() {

        long totalExpert = expertRegisterRequestRepository.countByStatus(ExpertRegisterRequestStatus.COMPLETE);
        long totalExpertRegister = expertRegisterRequestRepository.count();

        int currentMonth = LocalDateTime.now().getMonthValue();
        int currentYear = LocalDateTime.now().getYear();
        long newExpert = Optional.ofNullable(expertRegisterRequestRepository.countNewExpert(currentMonth, currentYear)).orElse(0L);

        return TotalExpertResponseDTO
                .builder()
                .totalExpertRegister(totalExpertRegister)
                .totalExpert(totalExpert)
                .newExpert(newExpert)
                .build();
    }

    @Override
    public List<TransactionAmountDashBoardResponseDTO> depositDashBoard(int year) {
        List<Object[]> results = transactionRepository.getTotalDepositAmountByMonth(year);
        return convertToTransactionAmountDashBoard(results);
    }

    @Override
    public List<TransactionAmountDashBoardResponseDTO> revenueDashBoard(int year) {
        List<Object[]> results = revenueRepository.getTotalAmountByMonth(year);

        return convertToTransactionAmountDashBoard(results);
    }

    @Override
    public List<CountCvTemplateDashBoardResponseDTO> countCvTemplate() {
        List<CountCvTemplateDashBoardResponseDTO> countCvTemplateDashBoardResponseDTOS = cvTemplateRepository.countCvTemplate();
        return Objects.requireNonNullElse(countCvTemplateDashBoardResponseDTOS, new ArrayList<>());
    }

    @Override
    public List<CountCvUsedDashBoardResponseDTO> countCv() {
        List<CountCvUsedDashBoardResponseDTO> list = new ArrayList<>();
        List<Object[]> results = cvRepository.countCvDashboard();
        if (results == null) {
            return list;
        }

        for (Object[] result : results) {
            list.add(
              CountCvUsedDashBoardResponseDTO
                      .builder()
                      .cvCategoryId((Long) result[0])
                      .cvCategory((String) result[1])
                      .countCv((Long) result[2])
                      .build()
            );
        }

        return list;
    }

    @Override
    public TotalCvResponseDTO totalCv() {
        long totalCv = cvRepository.countByStatus(1);
        long newCv = Optional.ofNullable(cvRepository.countByStatusAndCreatedAtMonth(1, LocalDateTime.now().getMonthValue())).orElse(0L);
        long cvBooking = Optional.ofNullable(bookingRepository.countDistinctCvId(BookingStatus.COMPLETE)).orElse(0L);

        return TotalCvResponseDTO
                .builder()
                .totalCv(totalCv)
                .newCv(newCv)
                .cvBooking(cvBooking)
                .build();
    }

    @Override
    public List<TopExpertResponseDTO> topExpert(int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<TopExpertResponseDTO> topExpert = bookingRepository.topExpert(BookingStatus.COMPLETE, pageable);
        return topExpert;
    }

    @Override
    public List<TopUseCvTemplateResponseDTO> topCvTemplate(String sort, int size) {
        Sort countUsage = Sort.by((sort.toLowerCase().equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC, "countUsage");
        Pageable pageable = PageRequest.of(0, size, countUsage);

        return cvTemplateRepository.getAndCountNumberUse(pageable);
    }

    @Override
    public List<BookingDashboardResponseDTO> bookingDashboard(int year) {

        return bookingRepository.bookingDashboardByStatusEachMonth(year);
    }

    @Override
    public List<ExpertDashboardResponseDTO> expertDashboard() {
        return expertSkillOptionRepository.countExpertByCategory();
    }

    private List<TransactionAmountDashBoardResponseDTO> convertToTransactionAmountDashBoard(List<Object[]> results) {
        if (results == null || results.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<TransactionAmountDashBoardResponseDTO> response = new ArrayList<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Double totalAmount = (Double) result[1];

            response.add(TransactionAmountDashBoardResponseDTO.builder().month(month).amount(totalAmount).build());
        }

        return response;
    }
}
