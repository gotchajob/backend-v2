package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BankInfo;
import com.example.gcj.Repository_Layer.repository.BankInfoRepository;
import com.example.gcj.Service_Layer.dto.bank_info.*;
import com.example.gcj.Service_Layer.mapper.BankInfoMapper;
import com.example.gcj.Service_Layer.service.BankInfoService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankInfoServiceImpl implements BankInfoService {
    private final BankInfoRepository bankInfoRepository;

    @Override
    public List<BankInfoListResponseDTO> get() {
        List<BankInfo> bankInfoList = bankInfoRepository.findAll();
        return bankInfoList.stream().map(BankInfoMapper::toDto).toList();
    }

    @Override
    public BankInfoResponseDTO getById(long id) {
        BankInfo bankInfo = get(id);

        return BankInfoResponseDTO
                .builder()
                .id(bankInfo.getId())
                .bankCode(bankInfo.getBankCode())
                .numberCard(bankInfo.getNumberCard())
                .nameHolder(bankInfo.getNameHolder())
                .status(bankInfo.getStatus())
                .accountId(bankInfo.getAccountId())
                .build();
    }

    @Override
    public CreateBankInfoResponseDTO create(CreateBankInfoRequestDTO request, long accountId) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        BankInfo build = BankInfo
                .builder()
                .bankCode(request.getBankCode())
                .numberCard(request.getNumberCard())
                .nameHolder(request.getNameHolder())
                .status(1)
                .accountId(accountId)
                .build();
        BankInfo save = save(build);

        return CreateBankInfoResponseDTO.builder().id(save.getId()).build();
    }

    @Override
    public boolean update(long id, UpdateBankInfoRequestDTO request, long accountId) {
        BankInfo bankInfo = get(id);
        if (bankInfo.getAccountId() != accountId) {
            throw new CustomException("current account not equal with account in bank info");
        }

        bankInfo.setBankCode(request.getBankCode());
        bankInfo.setNameHolder(request.getNameHolder());
        bankInfo.setNumberCard(request.getNumberCard());
        save(bankInfo);

        return true;
    }

    @Override
    public boolean delete(long id, long accountId) {
        BankInfo bankInfo = get(id);
        if (bankInfo.getAccountId() != accountId) {
            throw new CustomException("current account not equal with account in bank info");
        }

        bankInfo.setStatus(0);
        save(bankInfo);

        return true;
    }

    @Override
    public List<BankInfoListResponseDTO> getByAccount(long accountId) {
        List<BankInfo> bankInfoList = bankInfoRepository.findByAccountId(accountId);
        return bankInfoList.stream().map(BankInfoMapper::toDto).toList();
    }

    private BankInfo get(long id) {
        BankInfo bankInfo = bankInfoRepository.findById(id);
        if (bankInfo == null) {
            throw new CustomException("not found bank info");
        }

        return bankInfo;
    }

    private BankInfo save(BankInfo bankInfo) {
        if (bankInfo == null) {
            return null;
        }

        return bankInfoRepository.save(bankInfo);
    }
}
