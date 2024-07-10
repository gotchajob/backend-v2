package com.example.gcj.controller;

import com.example.gcj.dto.account.GetBalanceAccountResponseDTO;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

public class TemplateController {
    @GetMapping("")//todo: finish this
    @Operation(description = "")
    public Response<String> get(
    ) {

        return Response.ok(null);
    }

    @GetMapping("/{id}")//todo: finish this
    @Operation(description = "")
    public Response<String> getById(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @PostMapping("")//todo: finish this
    @Operation(description = "")
    public Response<String> create(

    ) {

        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured("role")//todo: finish this
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured("role")//todo: finish this
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }


}
