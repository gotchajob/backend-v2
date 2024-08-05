package com.example.gcj.service.impl;

import com.example.gcj.dto.payment_info.PaymentInfoResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.PaymentInfo;
import com.example.gcj.repository.PaymentInfoRepository;
import com.example.gcj.service.PaymentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentInfoServiceImpl implements PaymentInfoService {
    private final PaymentInfoRepository paymentInfoRepository;


    @Override
    public PaymentInfoResponseDTO getById(long id) {
        PaymentInfo paymentInfo = paymentInfoRepository.findById(id);
        if (paymentInfo == null) {
            throw new CustomException("not found payment info with id " + id);
        }

        return PaymentInfoResponseDTO
                .builder()
                .id(paymentInfo.getId())
                .bankCode(paymentInfo.getBankCode())
                .transactionNumber(paymentInfo.getTransactionNumber())
                .transactionInfo(paymentInfo.getTransactionInfo())
                .datePayment(paymentInfo.getDatePayment())
                .createdAt(paymentInfo.getCreatedAt())
                .build();
    }

    @Override
    public PaymentInfo save(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            throw new CustomException("request is null at save payment info");
        }

        return paymentInfoRepository.save(paymentInfo);
    }
}
