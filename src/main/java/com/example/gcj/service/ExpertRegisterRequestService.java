package com.example.gcj.service;


import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.expert_register_request.RejectFromRegisterRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;

public interface ExpertRegisterRequestService {
    void create(String email);
    PageResponseDTO<ExpertRegisterRequestResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search);
    ExpertRegisterRequestResponseDTO get(long id);
    void approveRegister(long id, String url);
    void rejectRegister(long id, String note);
    boolean checkRequest(long id);
    boolean banRequest(long id);

    boolean approveFormRegister(long id);
    boolean rejectFormRegister(long id, RejectFromRegisterRequestDTO request);
}
