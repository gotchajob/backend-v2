package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpertDashboardResponseDTO {
    private String category;
    private long countExpert;
}
