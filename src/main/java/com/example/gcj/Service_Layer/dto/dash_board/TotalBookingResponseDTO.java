package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalBookingResponseDTO {
    private long totalBooking;
    private long totalCompleteBooking;
    private double totalPayForService;
}
