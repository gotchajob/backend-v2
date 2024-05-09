package com.example.gcj.controller;

import com.example.gcj.dto.user.*;
import com.example.gcj.util.Response;
import com.example.gcj.model.User;
import com.example.gcj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
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
    public ResponseEntity<Response<List<User>>> get(

    ) {
        try {
            List<User> userList = userService.getAll();
            return Response.success(userList);

        } catch (Exception e) {
            return Response.error(e);
        }
    }



    @PostMapping("/create-mentor-account")
    public ResponseEntity<Response<String>> createMentorAccount(
        @RequestBody CreateMentorAccountRequestDTO requestDTO
    ) {
        try {
            userService.createMentorAccount(requestDTO);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }


    @GetMapping("/verify-mentor")
    public ResponseEntity<Response<GetMentorAccountResponseDTO>> getVerifyMentorList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        try {
            GetMentorAccountResponseDTO response = userService.getMentorAccountNotVerify(page, limit);
            return Response.success(response);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/approve-mentor")
    public ResponseEntity<Response<String>> approveMentor(
            @PathVariable long id
    ) {
        try {
            userService.updateMentorStatus(id, 1);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/reject-mentor")
    public ResponseEntity<Response<String>> rejectMentor(
            @PathVariable long id
    ) {
        try {
            userService.updateMentorStatus(id, 0);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
