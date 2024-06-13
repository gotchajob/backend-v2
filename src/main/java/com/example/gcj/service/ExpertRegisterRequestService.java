package com.example.gcj.service;


import com.example.gcj.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.expert_register_request.RejectFromRegisterRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.CreateExpertAccountRequestDTO;

public interface ExpertRegisterRequestService {
    void create(String email);
    PageResponseDTO<ExpertRegisterRequestResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search);
    ExpertRegisterRequestResponseDTO get(long id);
    void approveRegister(long id, String url);
    boolean checkRequest(long id);
    boolean banRequest(long id);
    boolean approveFormRegister(long id);
    boolean rejectFormRegister(long id, RejectFromRegisterRequestDTO request);
    boolean updateForm(long id, UpdateExpertRequestDTO request);
    boolean createForm(long id, CreateExpertAccountRequestDTO request);
}
