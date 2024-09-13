package com.example.gcj.Service_Layer.dto.dash_board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TopExpertResponseDTO {
    private long expertId;
    private String image;
    private String fullName;
    private String category;
    private long numberBooking;
}
