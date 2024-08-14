package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.CvShareService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cv-share")
@RequiredArgsConstructor
public class CvShareController { 
    private final CvShareService cvShareService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<PageResponseDTO<CvShareListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<CvShareListResponseDTO> response = cvShareService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @PostMapping("")
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateCvShareRequestDTO request
    ) {
        cvShareService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateCvShareRequestDTO request
    ) {
        cvShareService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        cvShareService.delete(id);
        return Response.ok(null);
    }
}
