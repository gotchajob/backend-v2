package com.example.gcj.controller;

import com.example.gcj.dto.cv.*;
import com.example.gcj.exception.CustomException;
import com.example.gcj.service.CvService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cv")
@RequiredArgsConstructor
public class CvController {
    private final CvService cvService;
    private final UserService userService;


    @GetMapping("/current")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<List<CVListResponseDTO>> getListByCurrentUser(

    ) {
        long userId = userService.getCurrentUserId();
        List<CVListResponseDTO> response = cvService.getByUserId(userId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CvResponseDTO> getById(
            @PathVariable long id
    ) {
        long userId = userService.getCurrentUserId();
        CvResponseDTO response = cvService.getById(userId, id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CreateCvResponseDTO> create(
            @RequestBody CreateCvRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        CreateCvResponseDTO response =  cvService.create(userId, request);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> updateCv(
            @PathVariable long id,
            @RequestBody UpdateCvRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        cvService.update(userId, id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> deleteCv(
            @PathVariable long id
    ) {
        long userId = userService.getCurrentUserId();
        cvService.delete(userId, id);
        return Response.ok(null);
    }

}
