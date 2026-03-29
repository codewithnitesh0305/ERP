package com.springboot.Payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String username;
}
