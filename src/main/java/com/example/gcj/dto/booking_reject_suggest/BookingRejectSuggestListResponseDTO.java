package com.example.gcj.dto.booking_reject_suggest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingRejectSuggestListResponseDTO {
    private long id;
    private String content;
}
