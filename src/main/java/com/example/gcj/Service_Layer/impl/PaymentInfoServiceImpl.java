package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.PaymentInfo;
import com.example.gcj.Repository_Layer.repository.PaymentInfoRepository;
import com.example.gcj.Service_Layer.dto.payment_info.PaymentInfoResponseDTO;
import com.example.gcj.Service_Layer.service.PaymentInfoService;
import com.example.gcj.Shared.exception.CustomException;
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
