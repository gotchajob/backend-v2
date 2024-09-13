package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TopUseCvTemplateResponseDTO {
    private long id;
    private String category;
    private long numberCv;
    private String name;
    private String image;
}
