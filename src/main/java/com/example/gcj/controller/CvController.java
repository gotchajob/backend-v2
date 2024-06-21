package com.example.gcj.controller;

import com.example.gcj.dto.cv.CVListResponseDTO;
import com.example.gcj.dto.cv.CreateCvRequestDTO;
import com.example.gcj.dto.cv.CvResponseDTO;
import com.example.gcj.dto.cv.UpdateCvRequestDTO;
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

    @GetMapping("/template")
    @Operation(description = "role: n/a")
    public Response<List<CVListResponseDTO>> getTemplate(
        @RequestParam(required = false) @Min(1) Long categoryId
    ) {
        List<CVListResponseDTO> response = cvService.getTemplateByCategoryId(categoryId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<CvResponseDTO> getById(
            @PathVariable long id
    ) {
        //neu la cv template thi khong can token, con lai thi can token
        CvResponseDTO response = cvService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    public Response<String> create(
            @RequestBody CreateCvRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        cvService.create(userId, request);
        return Response.ok(null);
    }

    @PostMapping("/template")
    @Secured({Role.ADMIN})
    @Operation(description = "role: admin")
    public Response<String> createTemplate(
            @RequestBody CreateCvRequestDTO request
    ) {
        cvService.create(null, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Operation(description = "role: user|admin")
    public Response<String> updateCv(
            @PathVariable long id,
            @RequestBody UpdateCvRequestDTO request
    ) {
        cvService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Operation(description = "role: user|admin")
    public Response<String> deleteCv(
            @PathVariable long id
    ) {
        cvService.delete(id);
        return Response.ok(null);
    }

}
