package com.example.gcj.Service_Layer.dto.booking;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookingListResponseDTO {
    private long id;
    private long expertId;
    private long customerId;
    //private long availabilityId;
    private LocalDateTime startInterviewDate;
    private LocalDateTime endInterviewDate;
    //private Long customerCvId;
    //private String note;
    //private String rejectReason;
    private int status;
    private java.util.Date createdAt;
    private boolean canCancel;

    //private List<Long> expertSkillOptionId;
}
