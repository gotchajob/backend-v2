package com.example.gcj.service;

import com.example.gcj.dto.payment_info.PaymentInfoResponseDTO;
import com.example.gcj.model.PaymentInfo;

public interface PaymentInfoService {

    PaymentInfoResponseDTO getById(long id);

    PaymentInfo save(PaymentInfo paymentInfo);
}
