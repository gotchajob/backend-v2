package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.service.ExpertNationSupportService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expert-nation-support")
@RequiredArgsConstructor
public class ExpertNationSupportController {
    private final ExpertNationSupportService expertNationSupportService;
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "admin, user")
    public Response<List<ExpertNationSupportResponseDTO>> getExpertNationSupport(
            @RequestParam long expertId
    ) {
        List<ExpertNationSupportResponseDTO> response = expertNationSupportService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(summary = "admin, user")
    public Response<List<ExpertNationSupportResponseDTO>> getCurrent(
    ) {
        long expertId = userService.getCurrentExpertId();
        List<ExpertNationSupportResponseDTO> response = expertNationSupportService.getByExpertId(expertId);
        return Response.ok(response);
    }
}
