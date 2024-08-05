package com.example.gcj.dto.payment_info;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
public class PaymentInfoResponseDTO {
    private long id;
    private String bankCode;
    private String transactionNumber;
    private String transactionInfo;
    private LocalDateTime datePayment;
    private Date createdAt;
}
