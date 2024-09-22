package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.CreateStaffRequestDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffListResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.StaffResponseDTO;
import com.example.gcj.Service_Layer.dto.staff.UpdateStaffRequestDTO;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import com.example.gcj.Shared.util.Util;
import com.example.gcj.Shared.util.status.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController { 
    private final UserService userService;

    @GetMapping("")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<PageResponseDTO<StaffListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        String[] customSearch = new String[]{"roleId:2", "status!0"};
        PageResponseDTO<StaffListResponseDTO> response = userService.getStaffList(pageNumber, pageSize, sortBy, Util.appendStringArrays(search, customSearch));
        return Response.ok(response);
    }

    @GetMapping("/all")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<List<StaffListResponseDTO>> get(

    ) {
        List<StaffListResponseDTO> response = userService.getAllStaffList();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<StaffResponseDTO> getById(
            @PathVariable long id
    ) {
        StaffResponseDTO response = userService.getStaffAccountById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateStaffRequestDTO request
    ) {
        userService.createStaffAccount(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateStaffRequestDTO request
    ) {
        userService.updateStaffAccount(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/disable")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<String> disable(
            @PathVariable long id
    ) {
        userService.updateStaffStatus(id, UserStatus.DISABLE);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/enable")
    @Secured(Role.ADMIN)
    @Operation(description = "finish")
    public Response<String> enableStaff(
            @PathVariable long id
    ) {
        userService.updateStaffStatus(id, UserStatus.ACTIVE);
        return Response.ok(null);
    }
}
