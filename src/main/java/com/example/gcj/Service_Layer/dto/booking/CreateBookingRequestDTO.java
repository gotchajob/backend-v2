package com.example.gcj.Service_Layer.dto.booking;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateBookingRequestDTO {
    @Min(1)
    private long expertId;
    @Min(1)
    private long availabilityId;
    private String note;
    @Min(1)
    private Long customerCvId;

    List<Long> bookingSkill;
}
