package com.example.gcj.dto.booking;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class BookingResponseDTO {
    private long id;
    private long expertId;
    private long customerId;
    private LocalDateTime startInterviewDate;
    private LocalDateTime endInterviewDate;
    private String note;
    private String rejectReason;
    private int status;
    private Date createdAt;

    private List<Long> expertSkillOptionIds;
}
