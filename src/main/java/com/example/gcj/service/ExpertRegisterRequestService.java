package com.example.gcj.service;


import com.example.gcj.dto.expert_register_request.GetExpertRegisterRequestResponseDTO;

public interface ExpertRegisterRequestService {
    void create(String email);
    GetExpertRegisterRequestResponseDTO get(int page, int limit);
    void approveRegister(long id, String url);

    void rejectRegister(long id, String note);
}
