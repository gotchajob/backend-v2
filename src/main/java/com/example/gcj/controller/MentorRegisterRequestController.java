package com.example.gcj.controller;

import com.example.gcj.dto.mentor_register_request.ApproveMentorRegisterRequestDTO;
import com.example.gcj.dto.mentor_register_request.CreateMentorRegisterRequestDTO;
import com.example.gcj.dto.mentor_register_request.GetMentorRegisterRequestResponseDTO;
import com.example.gcj.dto.mentor_register_request.RejectMentorRegisterRequestDTO;
import com.example.gcj.service.MentorRegisterRequestService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentor-register-request")
@RequiredArgsConstructor
@Tag(name = "Mentor register request controller")
public class MentorRegisterRequestController {
    private final MentorRegisterRequestService mentorRegisterRequestService;

    @PostMapping("")
    @Operation(summary = "admin, user")
    public ResponseEntity<Response<String>> mentorRegisterRequest(
            @RequestBody CreateMentorRegisterRequestDTO request
    ) {
        try {
            mentorRegisterRequestService.create(request.getEmail());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }
    @GetMapping("")
    public ResponseEntity<Response<GetMentorRegisterRequestResponseDTO>> get(
        @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
        @RequestParam(required = false, defaultValue = "6") @Min(1) int limit
    ) {
        try {
            GetMentorRegisterRequestResponseDTO responseDTO =  mentorRegisterRequestService.get(page, limit);
            return Response.success(responseDTO);

        } catch (Exception e) {
            return Response.error(e);
        }
    }
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Response<String>> approveRegister(
            @PathVariable long id,
            @RequestBody ApproveMentorRegisterRequestDTO requestDTO
    ) {
        try {
            mentorRegisterRequestService.approveRegister(id, requestDTO.getUrl());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Response<String>> rejectRegister(
            @PathVariable long id,
            @RequestBody(required = false) RejectMentorRegisterRequestDTO requestDTO
    ) {
        try {
            mentorRegisterRequestService.rejectRegister(id, requestDTO.getNote());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
