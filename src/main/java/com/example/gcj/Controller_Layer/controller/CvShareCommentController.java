package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv_share_comment.CreateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareCommentListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvCommentResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.UpdateCvShareCommentRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.CvShareCommentService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cv-comment")
@RequiredArgsConstructor
public class CvShareCommentController {
    private final CvShareCommentService cvShareCommentService;
    private final CustomerService customerService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<PageResponseDTO<CvShareCommentListResponseDTO>> get(

            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            //@RequestParam(required = false) String... search,
            @RequestParam long cvShareId
    ) {
        String searchByCvId = "cvShareId:" + cvShareId;
        PageResponseDTO<CvShareCommentListResponseDTO> response = cvShareCommentService.get(pageNumber, pageSize, sortBy, searchByCvId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<CvCommentResponseDTO> getById(
            @PathVariable long id
    ) {
        CvCommentResponseDTO response = cvShareCommentService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "finish. role: customer")
    public Response<String> create(
            @RequestBody @Valid CreateCvShareCommentRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareCommentService.create(customerId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateCvShareCommentRequestDTO request
    ) {
        cvShareCommentService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        cvShareCommentService.delete(id);
        return Response.ok(null);
    }
}
