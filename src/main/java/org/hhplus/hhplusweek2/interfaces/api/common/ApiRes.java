package org.hhplus.hhplusweek2.interfaces.api.common;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ApiRes<T> {

    /**
     * status
     */
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAIL = "fail";
    private static final String STATUS_ERROR = "error";

    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;


    public static <T> ApiRes<T> success(String message, T data) {
        ApiRes<T> response = ApiRes.<T>builder()
            .status(STATUS_SUCCESS)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
        return response;
    }

    public static <T> ApiRes<T> fail(String message, T data) {
        ApiRes<T> response = ApiRes.<T>builder()
            .status(STATUS_FAIL)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
        return response;
    }

    public static ApiRes error(String message) {
        ApiRes response = ApiRes.builder()
            .status(STATUS_ERROR)
            .message(message)
            .data(null)
            .timestamp(LocalDateTime.now())
            .build();
        return response;
    }

}
