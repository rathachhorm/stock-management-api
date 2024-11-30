package com.innovisor.quickpos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private HttpStatus status;
    private LocalDateTime dateTime;

    public static <T> ResponseEntity<?> body(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(message, data, status, LocalDateTime.now()));
    }
}
