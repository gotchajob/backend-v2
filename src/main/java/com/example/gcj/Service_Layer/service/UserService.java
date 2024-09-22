package com.example.gcj.Service_Layer.service;

import com.example.gcj.Repository_Layer.model.User;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.CreateStaffRequestDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffListResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.UpdateStaffRequestDTO;
import com.example.gcj.Service_Layer.dto.user.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    boolean updateProfile(UpdateUserProfileRequestDTO request);

    boolean createVerifyEmail(String email);

    boolean verifyEmail(String email, String code);

    boolean forgetPassword(String email, String code);

    boolean createForgetPassword(String email);

    boolean createStaffAccount(CreateStaffRequestDTO request);

    boolean updateStaffAccount(long id, UpdateStaffRequestDTO request);

    boolean updateStaffStatus(long id, int disable);

    StaffResponseDTO getStaffAccountById(long id);

    PageResponseDTO<StaffListResponseDTO> getStaffList(int pageNumber, int pageSize, String sortBy, String... search);

    List<StaffListResponseDTO> getAllStaffList();
}
