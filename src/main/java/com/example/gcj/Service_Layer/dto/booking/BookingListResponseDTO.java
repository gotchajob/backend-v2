package com.example.gcj.Service_Layer.dto.booking;

import com.example.gcj.Service_Layer.dto.user.UserBookingInfoResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingListResponseDTO {
    private long id;
    private long expertId;
    private long customerId;
    private LocalDateTime startInterviewDate;
    private LocalDateTime endInterviewDate;
    private String note;
    private int status;
    private java.util.Date createdAt;
    private boolean canCancel;

    private UserInfoResponseDTO expertInfo;
    private UserBookingInfoResponseDTO customerInfo;
}
