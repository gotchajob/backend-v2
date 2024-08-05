package com.example.gcj.util.service;

import com.example.gcj.config.vnpay.VNPAYConfig;
import com.example.gcj.dto.account.GetVnPayUrlResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Transaction;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.TransactionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.VNPayUtil;
import com.example.gcj.util.status.TransactionStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class VnPayService {
    private final VNPAYConfig vnPayConfig;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    public GetVnPayUrlResponseDTO createVnPayPayment(HttpServletRequest request) {
        String fontEndWeb = "http://localhost:3001/account-profile";//todo: flex this
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        long currentUserId = userService.getCurrentUserId();
        vnpParamsMap.put("vnp_ReturnUrl", fontEndWeb + "/" + currentUserId);
        long transactionId = transactionService.transactionForDeposit(amount/100, "deposit by vn pay");//todo fix this
        vnpParamsMap.put("vnp_TxnRef", String.valueOf(transactionId));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return GetVnPayUrlResponseDTO.builder().paymentURL(paymentUrl).build();
    }

    public void callBackVnPay(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        double amount = (Double.parseDouble(request.getParameter("vnp_Amount")) / 100);
        long transactionId = Long.parseLong(request.getParameter("vnp_TxnRef"));
        if (status.equals("00")) {
            //todo: check security
            Transaction transactionCheck = transactionService.getById(transactionId);
            if (transactionCheck.getStatus() != TransactionStatus.PROCESSING) {
                throw new CustomException("current transaction status is not processing");
            }

            Transaction transaction = transactionService.updateTransactionStatus(transactionId, TransactionStatus.COMPLETE);

            if (amount != transaction.getAmount()) {
                throw new CustomException("amount not same with transaction");
            }
            accountService.deposit(transaction.getAccountId(), transaction.getAmount());
        } else {
            throw new CustomException("invalid vnpay code");
        }

    }
}
