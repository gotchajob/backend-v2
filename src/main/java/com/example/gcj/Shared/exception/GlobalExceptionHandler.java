package com.example.gcj.Shared.exception;

import com.example.gcj.Shared.enums.Status;
import com.example.gcj.Shared.util.ErrorResponse;
import com.example.gcj.Shared.util.Response;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class,
            MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    public Response<ErrorResponse> handleValidationException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") + 1;
            int end = message.lastIndexOf("]") - 1;
            message = message.substring(start, end);
            errorResponse.setError("Invalid Payload");
            errorResponse.setMessage(message);
        } else if (e instanceof MissingServletRequestParameterException) {
            errorResponse.setError("Invalid Parameter");
            errorResponse.setMessage(message);
        } else if (e instanceof ConstraintViolationException) {
            errorResponse.setError("Invalid Parameter");
            errorResponse.setMessage(message.substring(message.indexOf(" ") + 1));
        } else {
            errorResponse.setError("Invalid Data");
            errorResponse.setMessage(message);
        }

        return new Response<>(Status.ERROR.name(), "dữ liệu yêu cầu không hợp lệ", errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(BAD_REQUEST)
    public Response<ErrorResponse> handleCustomException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError(BAD_REQUEST.getReasonPhrase());
        errorResponse.setStatus(BAD_REQUEST.value());

        return new Response<>(Status.ERROR.name(), e.getMessage(), errorResponse);
    }

    // Handle NoHandlerFoundException
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public Response<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError(NOT_FOUND.getReasonPhrase());
        errorResponse.setStatus(NOT_FOUND.value());

        return new Response<>(Status.WARNING.name(), "not found", errorResponse);
    }

    // Handle global exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Response<ErrorResponse> handleGlobalException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError(INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponse.setStatus(INTERNAL_SERVER_ERROR.value());

        String typeError = e.getClass().getName();
        return new Response<>(Status.ERROR.name(), "lỗi máy chủ", errorResponse);
    }


}
