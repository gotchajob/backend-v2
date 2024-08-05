package com.example.gcj.dto.availability;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class AvailabilityListResponseDTO {
    private long id;
    private long expertId;
    private int status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
