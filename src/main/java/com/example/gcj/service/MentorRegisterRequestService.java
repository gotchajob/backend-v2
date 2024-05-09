package com.example.gcj.service;


import com.example.gcj.dto.mentor_register_request.GetMentorRegisterRequestResponseDTO;

public interface MentorRegisterRequestService {
    void create(String email);
    GetMentorRegisterRequestResponseDTO get(int page, int limit);
    void approveRegister(long id, String url);

    void rejectRegister(long id, String note);
}
