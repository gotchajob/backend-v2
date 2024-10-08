package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.CreateBookingRejectSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.UpdateBookingRejectSuggestRequestDTO;
import com.example.gcj.Service_Layer.service.BookingRejectSuggestService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-reject-suggest")
@RequiredArgsConstructor
public class BookingRejectSuggestController {
    private final BookingRejectSuggestService bookingRejectSuggestService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<BookingRejectSuggestListResponseDTO>> get(
            @RequestParam(required = false) Integer type
    ) {
        List<BookingRejectSuggestListResponseDTO> response = bookingRejectSuggestService.get(type);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "")
    public Response<BookingRejectSuggestResponseDTO> getById(
            @PathVariable long id
    ) {
        BookingRejectSuggestResponseDTO response = bookingRejectSuggestService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "role: STAFF")
    public Response<String> create(
            @RequestBody @Valid CreateBookingRejectSuggestRequestDTO request
    ) {
        bookingRejectSuggestService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "role: STAFF")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateBookingRejectSuggestRequestDTO request
    ) {
        bookingRejectSuggestService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "role: STAFF")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        bookingRejectSuggestService.delete(id);
        return Response.ok(null);
    }
}
