package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountCvTemplateDashBoardResponseDTO {
    private long categoryId;
    private String category;
    private long countCvTemplate;
}
