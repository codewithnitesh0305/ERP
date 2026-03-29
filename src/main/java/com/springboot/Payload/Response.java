package com.springboot.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private String message;
    private Boolean status;
    private T data;

    public Response(String message,Boolean status, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
