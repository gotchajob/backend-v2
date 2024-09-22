package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.bank_info.*;

import java.util.List;

public interface BankInfoService {
    List<BankInfoListResponseDTO> get();

    BankInfoResponseDTO getById(long id);

    CreateBankInfoResponseDTO create(CreateBankInfoRequestDTO request, long accountId);

    boolean update(long id, UpdateBankInfoRequestDTO request, long accountId);

    boolean delete(long id, long accountId);

    List<BankInfoListResponseDTO> getByAccount(long accountId);
}
