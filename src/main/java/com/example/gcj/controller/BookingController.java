package com.example.gcj.controller;

import com.example.gcj.dto.booking.*;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.service.BookingService;
import com.example.gcj.service.CustomerService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    private final PolicyService policyService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<BookingListResponseDTO>> get(
    ) {
        List<BookingListResponseDTO> response = bookingService.getAll();
        return Response.ok(response);
    }

    @GetMapping("/customer/current")
    @Secured(Role.USER)
    @Operation(description = "booking status: 0-delete, 1-wait to expert accept, 2-wait to interview, 3-interviewing,4-wait to feedback, 5-complete, 6-cancel by customer, 7-cancel by expert, 8 reject")
    public Response<List<BookingListResponseDTO>> getByCurrent(
            @RequestParam(required = false) @Min(1) Integer status
    ) {
        long customerId = customerService.getCurrentCustomerId();
        List<BookingListResponseDTO> response = bookingService.getByCurrentAndStatus(customerId, status);
        return Response.ok(response);
    }

    @GetMapping("/expert/current")
    @Secured(Role.EXPERT)
    public Response<List<BookingListResponseDTO>> getByCurrentExpert(
            @RequestParam(required = false) @Min(1) Integer status
    ) {
        long expertId = expertService.getCurrentExpertId();
        List<BookingListResponseDTO> response = bookingService.getByExpertIdAndStatus(expertId, status);
        return Response.ok(response);
    }

    @GetMapping("/price")
    public Response<BookingPriceResponseDTO> getBookingPrice(
    ) {
        long price = policyService.getByKey(PolicyKey.BOOKING_PRICE, Long.class);
        return Response.ok(BookingPriceResponseDTO.builder().price(price).build());
    }

    @PatchMapping("/price")
    @Secured(Role.ADMIN)
    @Operation(description = "role: admin")
    public Response<String> updateBookingPrice(
        @RequestBody @Valid UpdateBookingPriceRequestDTO requestDTO
    ) {

        policyService.update(PolicyKey.BOOKING_PRICE, String.valueOf(requestDTO.getPrice()));
        return Response.ok(null);
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
        //todo: update availability status
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
    public Response<String> cancelByCustomer(
            @PathVariable long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingService.cancel(customerId, id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/cancel-by-expert")
    @Secured({Role.EXPERT})
    @Operation(description = "cancel by expert, will delete availability, only cancel when status is 2 and on valid time")
    public Response<String> cancelByExpert(
            @PathVariable long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingService.cancelByExpert(expertId, id);
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
