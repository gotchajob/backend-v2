package com.example.gcj.Service_Layer.dto.booking_reject_suggest;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class BookingRejectSuggestResponseDTO {
    private long id;
    private String content;
    private String description;
    private int type;
    private Date createdAt;
    private Date updatedAt;
}
