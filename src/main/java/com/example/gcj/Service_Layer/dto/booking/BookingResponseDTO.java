package com.example.gcj.Service_Layer.dto.booking;

import com.example.gcj.Service_Layer.dto.cv.CvBookingResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionBookingResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserBookingInfoResponseDTO;
import com.example.gcj.Service_Layer.dto.user.UserInfoResponseDTO;
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
    private CvBookingResponseDTO customerCv;
    private LocalDateTime startInterviewDate;
    private LocalDateTime endInterviewDate;
    private String note;
    private String rejectReason;
    private int status;
    private Date createdAt;
    private boolean canCancel;

    private List<SkillOptionBookingResponseDTO> skillOptionBooking;
    private UserBookingInfoResponseDTO customerInfo;
    private UserInfoResponseDTO expertInfo;
}
