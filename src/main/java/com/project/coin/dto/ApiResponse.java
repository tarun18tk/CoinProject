package com.project.coin.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//generic response type for all APIs
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timeStamp;

}
