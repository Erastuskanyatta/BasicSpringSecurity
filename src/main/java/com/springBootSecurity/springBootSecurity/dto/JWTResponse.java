package com.springBootSecurity.springBootSecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JWTResponse {

    public  String jwtToken;
    public  String refreshToken;
}
