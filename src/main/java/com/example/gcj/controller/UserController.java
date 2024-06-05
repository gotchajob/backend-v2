package com.example.gcj.controller;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.*;
import com.example.gcj.model.User;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import com.example.gcj.util.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @Operation(description = "Email: admin/user/expert + @gmail.com <br>Password: Test12345")
    public Response<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        LoginResponseDTO responseDTO = userService.login(request);
        return Response.ok(responseDTO);
    }

    @PostMapping("/signup")
    public Response<String> signup(
            @RequestBody SignupRequestDTO request
    ) {
        userService.signup(request);
        return Response.ok(null);
    }

    @PostMapping("/change-password")
    public Response<String> changePassword(

    ) {
        throw new RuntimeException("test");
    }

    @PostMapping("/forget-password")
    public Response<String> forgetPassword(

    ) {
        return Response.ok("null");
    }

    @GetMapping("")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public Response<PageResponseDTO<UserListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize
    ) {
        PageResponseDTO<UserListResponseDTO> userList = userService.getAll(pageNumber, pageSize);
        return Response.ok(userList);
    }

    @PostMapping("/create-expert-account")
    public Response<String> createExpertAccount(
            @RequestBody CreateExpertAccountRequestDTO requestDTO
    ) {
        userService.createExpertAccount(requestDTO);
        return Response.ok(null);
    }


    @GetMapping("/verify-expert")
    public Response<PageResponseDTO<ExpertAccountResponse>> getVerifyExpertList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        PageResponseDTO<ExpertAccountResponse> response = userService.getExpertAccountNotVerify(page, limit);
        return Response.ok(response);
    }



    @PatchMapping("/{id}/approve-expert")
    public Response<String> approveExpert(
            @PathVariable long id
    ) {
        userService.updateExpertStatus(id, 1);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject-expert")
    public Response<String> rejectExpert(
            @PathVariable long id
    ) {
        userService.updateExpertStatus(id, 0);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/ban")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public Response<String> banUser(
            @PathVariable long id
    ) {
        userService.banUser(id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/unban")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public Response<String> unbanUser(
            @PathVariable long id
    ) {
        userService.unbanUser(id);
        return Response.ok(null);
    }

    @GetMapping("/current")
    @Secured({Role.ADMIN, Role.USER, Role.EXPERT})
    @Operation(description = "role: admin/expert/user")
    public Response<UserProfileDTO> getCurrentUser(

    ) {
        User user = userService.currentUser();
        UserProfileDTO userProfile = UserMapper.toUserProfile(user);
        return Response.ok(userProfile);
    }


}
