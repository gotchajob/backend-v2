package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.*;
import com.example.gcj.Repository_Layer.model.User;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void signup(SignupRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO userLogin);
    PageResponseDTO<UserListResponseDTO> getAll(int pageNumber, int pageSize, String sortBy, String... search);
    long createExpertAccount(String email, CreateExpertAccountRequestDTO request);
    void updateExpertStatus(Long id, int status);
    void banUser(long id);
    void unbanUser(long id);
    User currentUser();
    String currentUserEmail();
    ExpertAccountResponse getExpert(long id);
    boolean isExistEmail(String email);
    long getCurrentExpertId();
    long getCurrentUserId();

    PageResponseDTO<UserListResponseDTO> getByUserAndExpert(Pageable pageable, String[] user, String[] expert);

    boolean changePassword(ChangePasswordRequestDTO request);
}
