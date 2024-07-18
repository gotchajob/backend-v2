package com.example.gcj.controller;

import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

public class TemplateController {
    @GetMapping("")
    @Operation(description = "coming soon")
    public Response<String> get(
    ) {

        return Response.ok(null);
    }

    @GetMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> getById(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @PostMapping("")
    @Operation(description = "coming soon")
    public Response<String> create(

    ) {

        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> delete(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }


}
