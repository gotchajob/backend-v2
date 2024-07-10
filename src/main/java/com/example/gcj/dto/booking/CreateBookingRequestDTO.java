package com.example.gcj.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class CreateBookingRequestDTO {
    private long expertId;
    private long availabilityId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "14:30:00")
    private LocalTime startInterviewTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "14:30:00")
    private LocalTime endInterviewTime;
    private LocalDate interviewDate;
    private String note;

    List<Long> bookingSkill;
}
