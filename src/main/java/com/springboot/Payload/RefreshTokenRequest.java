package com.springboot.Payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RefreshTokenRequest {
    private String refreshToken;
}
