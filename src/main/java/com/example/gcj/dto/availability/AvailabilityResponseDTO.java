package com.example.gcj.dto.availability;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AvailabilityResponseDTO {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
