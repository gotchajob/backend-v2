package com.example.gcj.Service_Layer.dto.booking_report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingExpertForCustomerResponseDTO {
    private long id;
    private String customerContent;
    private String customerEvidence;
    private String expertContent;
    private String expertEvidence;
    private String staffNote;
    private int status;
    private long bookingId;
}
