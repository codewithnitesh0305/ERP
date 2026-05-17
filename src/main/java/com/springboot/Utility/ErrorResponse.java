package com.springboot.Utility;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {

    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private String path;
}
