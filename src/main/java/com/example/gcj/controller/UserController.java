package com.example.gcj.controller;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.*;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.User;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import com.example.gcj.util.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @PatchMapping("/change-password")
    public Response<String> changePassword(
        @RequestBody @Valid ChangePasswordRequestDTO request
    ) {
        userService.changePassword(request);
        return Response.ok(null);
    }

    @PostMapping("/forget-password")
    public Response<String> forgetPassword(

    ) {
        return Response.ok("null");
    }

    @GetMapping("")
    //@Secured(Role.ADMIN)
    @Operation(description = "role: admin/super admin <br>" +
            "sortBy: (field):(asc|desc). example: id:asc <br>" +
            "search: (key)(:|<|>)(value). example: roleId:3, id>10")
    public Response<PageResponseDTO<UserListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<UserListResponseDTO> userList = userService.getAll(pageNumber, pageSize, sortBy, search);
        return Response.ok(userList);
    }

    @GetMapping("/advance-search")
    public Response<PageResponseDTO<UserListResponseDTO>> getByAdvanceSearch(
            Pageable pageable,
            @RequestParam(required = false) String[] user,
            @RequestParam(required = false) String[] expert
    ) {
        PageResponseDTO<UserListResponseDTO> userList = userService.getByUserAndExpert(pageable, user, expert);
        return Response.ok(userList);
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

    @GetMapping("/expert/{userId}")
    @Operation(description = "role: n/a")
    public Response<ExpertAccountResponse> getExpert(
        @PathVariable long userId
    ) {
        ExpertAccountResponse response = userService.getExpert(userId);
        return Response.ok(response);
    }

    @GetMapping("/check-email/{email}")
    @Operation(description = "role: n/a <br>" +
            "200: exist email")
    public Response<String> checkEmail(
            @PathVariable String email
    ) {
        boolean isExistEmail = userService.isExistEmail(email);
        if (!isExistEmail) {
            throw new CustomException("Email " + email + " is not exist!");
        }

        return Response.ok(null);
    }

}
