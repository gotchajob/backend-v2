package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv_share.CreateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareListResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.CvShareResponseDTO;
import com.example.gcj.Service_Layer.dto.cv_share.UpdateCvShareRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_share_comment.CvShareRatingListResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.CvShareService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import com.example.gcj.Shared.util.status.CvShareStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cv-share")
@RequiredArgsConstructor
public class CvShareController {
    private final CvShareService cvShareService;
    private final CustomerService customerService;

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

    @GetMapping("/view")
    @Operation(description = "finish")
    public Response<PageResponseDTO<CvShareListResponseDTO>> getView(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        if (search == null) {
            search = new String[]{};
        }
        String[] newSearch = Arrays.copyOf(search, search.length + 1);
        newSearch[newSearch.length-1] = "status:1";

        PageResponseDTO<CvShareListResponseDTO> response = cvShareService.get(pageNumber, pageSize, sortBy, newSearch);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.USER)
    @Operation(description = "")
    public Response<PageResponseDTO<CvShareListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Long cvId
    ) {
        long customerId = customerService.getCurrentCustomerId();
        String[] search = new String[]{
                "status!0",
                cvId != null ? "cvId:" + cvId : "",
                "customerId:" + customerId
        };

        PageResponseDTO<CvShareListResponseDTO> response = cvShareService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "")
    public Response<CvShareResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        CvShareResponseDTO response = cvShareService.getById(id, customerId);
        return Response.ok(response);
    }

    @GetMapping("/{id}/rating")
    @Secured(Role.USER)
    @Operation(description = "")
    public Response<List<CvShareRatingListResponseDTO>> getRating(
            @PathVariable @Min(1) long id
    ) {
        List<CvShareRatingListResponseDTO> response = cvShareService.getRating(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateCvShareRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareService.create(request, customerId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateCvShareRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareService.update(id, request, customerId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/hidden")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> hidden(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareService.updateStatus(id, CvShareStatus.HIDDEN, customerId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/cancel-hidden")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> cancelHidden(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareService.updateStatus(id, CvShareStatus.SHARE, customerId);
        return Response.ok(null);
    }


    @DeleteMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvShareService.delete(id, customerId);
        return Response.ok(null);
    }
}
