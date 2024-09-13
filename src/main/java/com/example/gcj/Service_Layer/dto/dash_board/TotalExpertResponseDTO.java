package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalExpertResponseDTO {
    private long totalExpert;
    private long newExpert;
    private long totalExpertRegister;
}
