package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositDashBoardResponseDTO {
    private int month;
    private double money;
}
