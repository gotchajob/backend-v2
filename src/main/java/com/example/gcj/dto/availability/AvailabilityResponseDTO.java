package com.example.gcj.dto.availability;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailabilityResponseDTO {
    private long id;
    private long date;
    private long startTime;
    private long endTime;

}
