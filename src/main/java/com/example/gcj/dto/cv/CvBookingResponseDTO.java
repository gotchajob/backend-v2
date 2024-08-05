package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CvBookingResponseDTO {
    private long id;
    private String name;
    private String image;
}
