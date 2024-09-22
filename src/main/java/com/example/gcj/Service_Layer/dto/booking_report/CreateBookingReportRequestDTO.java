package com.example.gcj.Service_Layer.dto.booking_report;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateBookingReportRequestDTO {
    @Min(1)
    private long bookingId;
    @NotBlank
    private String content;
    private String evidence;

    private List<Long> reportSuggestIds;
}
