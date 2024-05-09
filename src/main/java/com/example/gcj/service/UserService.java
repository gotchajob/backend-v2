package com.example.gcj.service;

import com.example.gcj.dto.user.*;
import com.example.gcj.model.User;

import java.util.List;

public interface UserService {
    void signup(SignupRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO userLogin);
    List<User> getAll();
    void createMentorAccount(CreateMentorAccountRequestDTO request);
    GetMentorAccountResponseDTO getMentorAccountNotVerify(int page, int limit);
    void updateMentorStatus(Long id, int status);

    //change/
}
