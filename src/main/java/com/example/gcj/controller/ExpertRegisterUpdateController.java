package com.example.gcj.controller;


import com.example.gcj.dto.expert_request_reject.ExpertRegisterUpdateRequestDTO;
import com.example.gcj.model.ExpertRegisterUpdate;
import com.example.gcj.service.ExpertRegisterUpdateService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expert-register-form")
@RequiredArgsConstructor
@Tag(name = "Expert Register Form Controller")
public class ExpertRegisterUpdateController {
    private final ExpertRegisterUpdateService expertRegisterUpdateService;

    @PatchMapping("/confirm")
    @Secured({Role.ADMIN})
    public Response<ExpertRegisterUpdate> updateExpertForm(@RequestBody ExpertRegisterUpdateRequestDTO request) {
        expertRegisterUpdateService.updateUpdateExpertRequest(request);
        return Response.ok(null);
    }
}
