package com.example.gcj.Service_Layer.dto.booking;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateBookingRequestDTO {
    private long expertId;
    private long availabilityId;
    private String note;
    private Long customerCvId;

    List<Long> bookingSkill;
}
