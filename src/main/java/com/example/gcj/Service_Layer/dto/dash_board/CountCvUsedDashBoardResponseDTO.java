package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountCvUsedDashBoardResponseDTO {
    private long cvCategoryId;
    private String cvCategory;
    private long countCv;
}
