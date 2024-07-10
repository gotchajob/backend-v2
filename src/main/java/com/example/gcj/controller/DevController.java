package com.example.gcj.controller;

import com.example.gcj.dto.user.LoginRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
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
