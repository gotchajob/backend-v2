package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalCvResponseDTO {
    private long totalCv;
    private long newCv;
    private long cvBooking;
}
