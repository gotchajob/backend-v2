package com.example.gcj.controller;

import com.example.gcj.dto.booking.*;
import com.example.gcj.service.BookingService;
import com.example.gcj.service.CustomerService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<BookingListResponseDTO>> get(
    ) {
        List<BookingListResponseDTO> response = bookingService.getAll();
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.USER)
    @Operation(description = "booking status: 0-delete, 1-wait to expert accept, 2-wait to interview, 3-interviewing,4-wait to feedback, 5-complete, 6-cancel by customer, 7-cancel by expert, 8 reject")
    public Response<List<BookingListResponseDTO>> getByCurrent(
    ) {
        long customerId = customerService.getCurrentCustomerId();
        List<BookingListResponseDTO> response = bookingService.getByCurrent(customerId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "")
    public Response<BookingResponseDTO> getById(
            @PathVariable long id
    ) {
        BookingResponseDTO response = bookingService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "")
    public Response<String> create(
            @RequestBody CreateBookingRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingService.create(customerId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured("role")
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateBookingRequestDTO request
    ) {

        bookingService.update(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/cancel")
    @Secured({Role.USER})
    @Operation(description = "cancel by customer")
    public Response<String> cancelBooking(
            @PathVariable long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingService.cancel(customerId, id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/accept")
    @Secured({Role.EXPERT})
    @Operation(description = "for expert only")
    public Response<String> acceptBooking(
            @PathVariable long id
    ) {
        long currentExpertId = expertService.getCurrentExpertId();
        bookingService.approve(currentExpertId, id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject")
    @Secured({Role.EXPERT})
    @Operation(description = "for expert when status is 1")
    public Response<String> rejectBooking(
            @PathVariable long id,
            @RequestBody RejectBookingRequestDTO request
    ) {
        long currentExpertId = expertService.getCurrentExpertId();
        bookingService.reject(currentExpertId, id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {
        bookingService.delete(id);
        return Response.ok(null);
    }
}
