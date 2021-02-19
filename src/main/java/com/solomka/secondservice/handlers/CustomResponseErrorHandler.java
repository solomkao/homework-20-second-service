package com.solomka.secondservice.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus status = clientHttpResponse.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws CustomException {
        throw new CustomException("Smth bad happened");
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws CustomException {
        throw new CustomException("Smth bad happened "+url.toString());
    }

    @ExceptionHandler
    public ResponseEntity<RequestErrorResponse> handleException(CustomException exc) {
        RequestErrorResponse error = new RequestErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    static class CustomException extends RuntimeException {
        public CustomException(String message) {
            super(message);
        }
    }
}
