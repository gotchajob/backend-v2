package com.example.gcj.dto.booking_reject_suggest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBookingRejectSuggestRequestDTO {
    private String content;
    private String description;
    private int type;
}
