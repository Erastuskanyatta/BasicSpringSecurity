package com.springBootSecurity.springBootSecurity.services;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    public  String username;
    public  String password;
    public  String email;
}
