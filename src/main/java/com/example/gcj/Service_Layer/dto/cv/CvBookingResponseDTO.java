package com.example.gcj.Service_Layer.dto.cv;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CvBookingResponseDTO {
    private long id;
    private String name;
    private String image;
}
