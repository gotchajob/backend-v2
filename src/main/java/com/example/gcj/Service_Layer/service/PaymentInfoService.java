package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.payment_info.PaymentInfoResponseDTO;
import com.example.gcj.Repository_Layer.model.PaymentInfo;

public interface PaymentInfoService {

    PaymentInfoResponseDTO getById(long id);

    PaymentInfo save(PaymentInfo paymentInfo);
}
