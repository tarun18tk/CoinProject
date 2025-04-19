package com.project.coin.exception;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.project.coin.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponse<Object>> handleWebClientResponseException(WebClientResponseException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ApiResponse.builder()
                        .success(false)
                        .message("External API error: " + ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleWebClientRequestException(WebClientRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.builder()
                        .success(false)
                        .message("External service unreachable")
                        .data(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message("An unexpected error occurred"+ ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }
}
