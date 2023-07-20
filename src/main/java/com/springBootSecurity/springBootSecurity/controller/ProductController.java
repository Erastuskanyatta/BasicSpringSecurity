package com.springBootSecurity.springBootSecurity.controller;


import com.springBootSecurity.springBootSecurity.dto.ProductDto;
import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.services.JwtRequest;
import com.springBootSecurity.springBootSecurity.services.JwtUtility;
import com.springBootSecurity.springBootSecurity.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
   public ProductService productService;
    @Autowired
    public  JwtUtility jwtUtility;

    @Autowired
    public AuthenticationManager authenticationManager;

    @GetMapping("/greetings")
    public  String Greeting(){
        return "Greetings at User!";
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/products/all")
    public List<ProductDto> allProducts(){
        return  productService.getProducts();
    }
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/products/{id}")
    public  ProductDto getProductById(@PathVariable int id){
        return  productService.getProductById(id);
    }

    //save User
    @PostMapping("/uploadUser")
    public AppUser appUser(@RequestBody AppUser appUser){
        return  productService.uploadUser(appUser);

    }

    //login
    @PostMapping("/authenticateUser")
    public String Authenticate(@RequestBody JwtRequest  jwtRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtility.generateToken(jwtRequest.getUsername());

        }else {
            throw new UsernameNotFoundException("user not found");
        }

    }
}
