package com.example.gcj.Service_Layer.dto.booking_reject_suggest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBookingRejectSuggestRequestDTO {
    @NotBlank
    private String content;
    private String description;
    @Min(1)
    @Max(2)
    private int type;
}
