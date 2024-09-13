package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalMoneyTypeResponseDTO {
    private double totalDeposit;
    private double totalRevenue;
    private double totalWithDraw;
}
