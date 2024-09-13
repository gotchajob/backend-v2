package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionAmountDashBoardResponseDTO {
    private int month;
    private double amount;
}
