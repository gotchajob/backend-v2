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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @Operation(description = "Email: admin/user/expert + @gmail.com <br>Password: Test12345")
    public ResponseEntity<Response<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO request
    ) {
        try {
            LoginResponseDTO responseDTO = userService.login(request);
            return Response.success(responseDTO);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<String>> signup(
            @RequestBody SignupRequestDTO request
    ) {
        try {
            userService.signup(request);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response<String>> changePassword(

    ) {
        try {
            throw new RuntimeException("test");
            //return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<Response<String>> forgetPassword(

    ) {
        try {

            return Response.success("null");


        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public ResponseEntity<Response<PageResponseDTO<UserListResponseDTO>>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int pageSize
    ) {
        try {
            PageResponseDTO<UserListResponseDTO> userList = userService.getAll(pageNumber, pageSize);
            return Response.success(userList);

        } catch (Exception e) {
            return Response.error(e);
        }
    }


    @PostMapping("/create-expert-account")
    public ResponseEntity<Response<String>> createExpertAccount(
            @RequestBody CreateExpertAccountRequestDTO requestDTO
    ) {
        try {
            userService.createExpertAccount(requestDTO);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }


    @GetMapping("/verify-expert")
    public ResponseEntity<Response<PageResponseDTO<ExpertAccountResponse>>> getVerifyExpertList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        try {
            PageResponseDTO<ExpertAccountResponse> response = userService.getExpertAccountNotVerify(page, limit);
            return Response.success(response);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/approve-expert")
    public ResponseEntity<Response<String>> approveExpert(
            @PathVariable long id
    ) {
        try {
            userService.updateExpertStatus(id, 1);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/reject-expert")
    public ResponseEntity<Response<String>> rejectExpert(
            @PathVariable long id
    ) {
        try {
            userService.updateExpertStatus(id, 0);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/ban")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public ResponseEntity<Response<String>> banUser(
            @PathVariable long id
    ) {
        try {
            userService.banUser(id);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/unban")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin")
    public ResponseEntity<Response<String>> unbanUser(
            @PathVariable long id
    ) {
        try {
            userService.unbanUser(id);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
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
