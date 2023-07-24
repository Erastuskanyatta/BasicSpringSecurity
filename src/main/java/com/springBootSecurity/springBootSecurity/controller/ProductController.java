package com.springBootSecurity.springBootSecurity.controller;

import com.springBootSecurity.springBootSecurity.dto.JWTResponse;
import com.springBootSecurity.springBootSecurity.dto.ProductDto;
import com.springBootSecurity.springBootSecurity.dto.RefreshTokenDto;
import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.model.RefreshToken;
import com.springBootSecurity.springBootSecurity.services.JwtRequest;
import com.springBootSecurity.springBootSecurity.services.JwtUtility;
import com.springBootSecurity.springBootSecurity.services.ProductService;
import com.springBootSecurity.springBootSecurity.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProductController {
    @Autowired
    public RefreshTokenService refreshTokenService;
    @Autowired
   public ProductService productService;
    @Autowired
    public  JwtUtility jwtUtility;

    @Autowired
    public AuthenticationManager authenticationManager;

    @GetMapping("/user/greetings")
    public  String Greeting(){
        return "Greetings! This end-point is not secure";
    }
    @GetMapping("/hello/greetings")
    public  String Hello(){
        return "Greetings! This end-point is secure";
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/products/all")
    public List<ProductDto> allProducts(){
        return  productService.getProducts();
    }
    @PreAuthorize("hasAuthority('User') OR hasAuthority('Admin')")
    @GetMapping("/products/{id}")
    public  ProductDto getProductById(@PathVariable int id){
        return  productService.getProductById(id);
    }

    //save User
    @PostMapping("/user/uploadUser")
    public AppUser appUser(@RequestBody AppUser appUser){
        return  productService.uploadUser(appUser);
    }

    //login
    @PostMapping("/user/authenticateUser")
    public JWTResponse Authenticate(@RequestBody JwtRequest  jwtRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
           RefreshToken refreshToken = refreshTokenService.createRefreshToken(jwtRequest.getUsername());
            return  JWTResponse.builder()
                    .jwtToken(jwtUtility.generateToken(jwtRequest.getUsername()))
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }else {
            throw new UsernameNotFoundException("user not found");
        }
    }


    //refresh token
    @PostMapping("/user/refreshToken")
    public JWTResponse createRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
      return refreshTokenService.findByRefreshToken(refreshTokenDto.getRefreshToken())
               .map(refreshTokenService::verifyExpiry)
               .map(RefreshToken::getAppUser)
               .map(appUser -> {
                  String  newToken=  jwtUtility.generateToken(appUser.getUsername());
                   return  JWTResponse.builder()
                           .jwtToken(newToken)
                           .refreshToken(refreshTokenDto.getRefreshToken())
                           .build();
               }).orElseThrow(()->new RuntimeException("That refresh Token does not exist"));
    }
}
