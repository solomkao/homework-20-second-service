package com.solomka.secondservice.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timeStamp;
}
