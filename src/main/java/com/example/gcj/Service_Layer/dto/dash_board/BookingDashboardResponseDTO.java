package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingDashboardResponseDTO {
    private int month;
    private long bookingFinish;
    private long bookingCancelByExpert;
    private long bookingCancelByCustomer;
}
