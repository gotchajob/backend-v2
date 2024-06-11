package com.example.gcj.service;


import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.other.PageResponseDTO;

public interface ExpertRegisterRequestService {
    void create(String email);
    PageResponseDTO<ExpertRegisterRequestResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search);
    ExpertRegisterRequestResponseDTO get(long id);
    void approveRegister(long id, String url);
    void rejectRegister(long id, String note, String url);
    boolean checkRequest(long id);
}
