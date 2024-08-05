package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.user.LoginRequestDTO;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {
    private final UserService userService;

    @GetMapping("get-token")
    public String getToken(@RequestParam int role) {
        String email = "";
        switch (role) {
            case 1 -> email += "admin";
            case 2 -> email += "staff";
            case 3 -> email += "expert";
            case 4 -> email += "user";
            default -> throw new CustomException("invalid role");
        }
        return userService.login(new LoginRequestDTO(email + "@gmail.com", "Test12345")).getToken();
    }
}
