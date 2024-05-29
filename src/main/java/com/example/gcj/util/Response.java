package com.example.gcj.util;

import com.example.gcj.enums.Status;
import com.example.gcj.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class Response<T> {
    private String status;
    private String responseText;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public Response(String status, String responseText, T data) {
        this.status = status;
        this.responseText = responseText;
        this.data = data;
    }

    public static <T> ResponseEntity<Response<T>> error(Exception e) {
        if (e instanceof CustomException) {
            Response<T> error = new Response<>(Status.WARNING.name().toLowerCase(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(error);
        }

        System.out.println("Error: " + e.getMessage());
        Response<T> error = new Response<>(Status.ERROR.name().toLowerCase(), "Error: " + e.getMessage(), null);
        return ResponseEntity.internalServerError().body(error);
    }

    public static <T> ResponseEntity<Response<T>> success(T data) {
        Response<T> response = new Response<>(Status.SUCCESS.name().toLowerCase(), "success", data);
        return ResponseEntity.ok(response);
    }

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>(Status.SUCCESS.name().toLowerCase(), "success", data);
        return response;
    }
}
