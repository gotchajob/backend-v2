package com.example.gcj.util;

import com.example.gcj.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

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

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>(Status.SUCCESS.name().toLowerCase(), "success", data);
        return response;
    }
}
