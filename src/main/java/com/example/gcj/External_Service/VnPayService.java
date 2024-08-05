package com.example.gcj.External_Service;

import com.example.gcj.Repository_Layer.model.PaymentInfo;
import com.example.gcj.Repository_Layer.model.Transaction;
import com.example.gcj.Repository_Layer.repository.PaymentInfoRepository;
import com.example.gcj.Service_Layer.dto.account.GetVnPayUrlResponseDTO;
import com.example.gcj.Service_Layer.service.AccountService;
import com.example.gcj.Service_Layer.service.TransactionService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.VNPayUtil;
import com.example.gcj.Shared.util.status.TransactionStatus;
import com.example.gcj.Shared.config.vnpay.VNPAYConfig;
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
    private final PaymentInfoRepository paymentInfoRepository;


    public GetVnPayUrlResponseDTO createVnPayPayment(HttpServletRequest request) {
        String fontEndWeb = "https://www.gotchajob.vn/account-profile";
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
        long transactionId = Long.parseLong(request.getParameter("vnp_TxnRef"));

//        if (!VNPayUtil.verifySignature(request, vnPayConfig.getSecretKey())) {
//            throw new CustomException("invalid signature");
//        }

        PaymentInfo build = PaymentInfo
                .builder()
                .transactionInfo(request.getParameter("vnp_OrderInfo"))
                .datePayment(VNPayUtil.parseVnPayDate(request.getParameter("vnp_PayDate")))
                .bankCode(request.getParameter("vnp_BankCode"))
                .transactionNumber(request.getParameter("vnp_TransactionNo"))
                .build();
        PaymentInfo paymentInfo = paymentInfoRepository.save(build);

        if (status.equals("00")) {
            Transaction transactionCheck = transactionService.getById(transactionId);
            if (transactionCheck.getStatus() != TransactionStatus.PROCESSING) {
                throw new CustomException("current transaction status is not processing");
            }

            Transaction transaction = transactionService.updateTransactionStatus(transactionId, TransactionStatus.COMPLETE, paymentInfo.getId());
            accountService.deposit(transaction.getAccountId(), transaction.getAmount());
        } else {
            transactionService.updateTransactionStatus(transactionId, TransactionStatus.FAIL, paymentInfo.getId());
        }

    }

}
