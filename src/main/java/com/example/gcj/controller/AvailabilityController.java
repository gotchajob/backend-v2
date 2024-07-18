package com.example.gcj.controller;

import com.example.gcj.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.dto.availability.CreateAvailabilityListRequestDTO;
import com.example.gcj.service.AvailabilityService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> get(
            @RequestParam(required = false) @Min(1) Long expertId
    ) {
        List<AvailabilityListResponseDTO> response = availabilityService.get(expertId);
        return Response.ok(response);
    }

    @GetMapping("/expert/{expertId}/valid-date-to-booking")
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> getValidDate(
            @PathVariable long expertId
    ) {
        List<AvailabilityListResponseDTO> response = availabilityService.getValidDateToBooking(expertId);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> getByCurrent(

    ) {
        long expertId = expertService.getCurrentExpertId();
        List<AvailabilityListResponseDTO> response = availabilityService.get(expertId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "")//todo: finish this
    public Response<AvailabilityResponseDTO> getById(
            @PathVariable long id
    ) {
        AvailabilityResponseDTO responseDTO = availabilityService.getById(id);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<String> create(
        @RequestBody CreateAvailabilityListRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        availabilityService.create(expertId, request.getRequest());
        return Response.ok(null);
    }

    @PatchMapping("/{id}")//todo: finish this
    @Secured("role")
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        availabilityService.delete(id, expertId);
        return Response.ok(null);
    }

}
